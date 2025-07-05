package wavelog.wavelog.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteResponse {
    private Long id;
    // 삭제 메세지
    private String message;
}
