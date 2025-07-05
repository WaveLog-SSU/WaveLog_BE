package wavelog.wavelog.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ViewResponse {
    private Long id;
    private String title;
    private String code;
    private String content;
    private String category;
    private List<String> hashtags;
    private String wavelogId;
    private String nickname;
    private String profileImageUrl;
//    private String introIndex;
    private LocalDateTime createdAt;
    private int viewCount;
    private int likeCount;
}
