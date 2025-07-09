package wavelog.wavelog.domain.hashtag.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRequest {
    private Long id;
    private String tag;
}
