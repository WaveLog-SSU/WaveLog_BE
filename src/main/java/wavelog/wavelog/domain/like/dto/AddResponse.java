package wavelog.wavelog.domain.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddResponse {
    private Long diaryId;
    private Integer likeCount;
    private boolean likeCheck;
}
