package wavelog.wavelog.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CreateResponse {
    private Long id;
    private String title;
    private String code;
    private String content;
    private LocalDateTime createdAt;
    private String category;
    private List<String> hashtags;
    // 작성자
    private String wavelogId;
    private String nickname;
}
