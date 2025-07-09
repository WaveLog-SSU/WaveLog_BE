package wavelog.wavelog.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;
import wavelog.wavelog.domain.member.dto.*;
import wavelog.wavelog.global.jwt.JwtTokenProvider;
import wavelog.wavelog.global.jwt.dto.JwtToken;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {

        //중복 검사
        if (memberRepository.existsByWavelogId(request.getWavelogId())) {
            throw new IllegalArgumentException("이미 사용 중인 Wavelog ID 입니다.");
        }
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // db 저장
        Member member = Member.builder()
                .wavelogId(request.getWavelogId())
                .name(request.getName())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .profileImageUrl(request.getProfileImageUrl())
                .introIndex(request.getIntroIndex())
                .build();
        member = memberRepository.save(member);

        // DTO 반환
        return SignUpResponse.builder()
                .id(member.getId())
                .wavelogId(member.getWavelogId())
                .name(member.getName())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Member member = memberRepository.findByWavelogId(request.getWavelogId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

            // 토큰 발급
            JwtToken jwt = tokenProvider.generateToken(member.getId().toString());

            return LoginResponse.builder()
                    .grantType(jwt.getGrantType())
                    .accessToken(jwt.getAccessToken())
                    .build();

    }

    @Override
    public GetMemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."));
        return GetMemberResponse.builder()
                .wavelogId(member.getWavelogId())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .introIndex(member.getIntroIndex())
                .build();
    }
}
