package wavelog.wavelog.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteResponse {
    private Long id;
    private String message;
    private boolean bookmarkCheck;
    private int bookmarkCount;
}
