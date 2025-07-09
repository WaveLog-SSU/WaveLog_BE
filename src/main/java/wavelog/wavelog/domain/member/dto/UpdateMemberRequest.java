package wavelog.wavelog.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberRequest {
    private String nickname;

    private String profileImageUrl;

    private String introIndex;
}
