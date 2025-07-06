package wavelog.wavelog.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class GetCommentResponse {
    private Long id;
    private String content;
    private Long parentCommentId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<GetCommentResponse> replies = new ArrayList<>();
}
