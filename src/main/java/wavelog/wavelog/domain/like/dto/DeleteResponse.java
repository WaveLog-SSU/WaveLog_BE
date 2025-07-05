package wavelog.wavelog.domain.like.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DeleteResponse {
    private String message;
    private boolean likeCheck;
}
