package wavelog.wavelog.global.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wavelog.wavelog.domain.member.domain.entity.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    // memberId와 같은 정보에 접근 가능하도록
    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public Long getMemberId() {
        return member.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getWavelogId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
