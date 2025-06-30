package wavelog.wavelog.domain.member.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String wavelogId;
    private String password;
}
