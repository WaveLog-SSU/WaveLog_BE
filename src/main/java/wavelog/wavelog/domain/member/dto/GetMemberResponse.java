package wavelog.wavelog.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMemberResponse {
    private String wavelogId;

    private String name;

    private String nickname;

    private String profileImageUrl;

    private String introIndex;
}
