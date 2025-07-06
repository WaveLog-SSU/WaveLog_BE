package wavelog.wavelog.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpdateCommentRequest {
    private String content;
}