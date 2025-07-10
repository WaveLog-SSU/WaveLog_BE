package wavelog.wavelog.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountDiaryResponse {
    private Long count;
}
