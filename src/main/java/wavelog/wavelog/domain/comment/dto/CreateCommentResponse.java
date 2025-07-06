package wavelog.wavelog.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateCommentResponse {
    private Long id;
    private String content;
    private Long parentCommentId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
