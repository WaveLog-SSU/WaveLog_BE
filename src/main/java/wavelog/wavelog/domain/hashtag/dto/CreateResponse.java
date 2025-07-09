package wavelog.wavelog.domain.hashtag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateResponse {
    private Long id;
    private String tag;
}
