package wavelog.wavelog.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.domain.member.domain.repository.MemberRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {
        Long memberId;
        try {
            memberId = Long.valueOf(subject);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID: " + subject, e);
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " 사용자를 찾을 수 없습니다."));
        return User.builder()
                .username(member.getWavelogId())
                .password(member.getPassword())
                .authorities(Collections.emptyList())  // 권한이 없다면 빈 리스트
                .build();
    }
}