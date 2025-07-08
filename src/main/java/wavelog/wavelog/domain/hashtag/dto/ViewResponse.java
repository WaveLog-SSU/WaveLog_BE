package wavelog.wavelog.domain.hashtag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViewResponse {
    private Long id;
    private String tag;
}
