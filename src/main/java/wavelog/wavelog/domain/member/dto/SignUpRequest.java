package wavelog.wavelog.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String wavelogId;
    private String name;
    private String password;
    private String nickname;
    private String profileImageUrl;
    private String introIndex;
}
