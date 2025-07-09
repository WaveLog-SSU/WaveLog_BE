package wavelog.wavelog.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddResponse {
    private Long id;
    private Long diaryId;
    private Long memberId;
    private String message;
    private int bookmarkCount;
    private boolean bookmarkCheck;
}
