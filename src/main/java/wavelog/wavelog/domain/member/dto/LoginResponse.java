package wavelog.wavelog.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String grantType;
    private String accessToken;
    private Long memberId;
}
