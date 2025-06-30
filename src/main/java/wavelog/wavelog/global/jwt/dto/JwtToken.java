package wavelog.wavelog.global.jwt.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {
    private String grantType;
    private String accessToken;
}
