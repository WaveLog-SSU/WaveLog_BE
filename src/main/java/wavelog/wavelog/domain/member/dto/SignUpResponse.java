package wavelog.wavelog.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
    private Long id;
    private String wavelogId;
    private String name;
}
