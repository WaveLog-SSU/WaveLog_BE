package wavelog.wavelog.domain.hashtag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteResponse {
    private Long id;
    private String message;
}
