package wavelog.wavelog.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;
import wavelog.wavelog.domain.member.dto.SignUpRequest;
import wavelog.wavelog.domain.member.dto.SignUpResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
}
