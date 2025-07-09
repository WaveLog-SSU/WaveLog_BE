package wavelog.wavelog.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteResponse {
    // 삭제 메세지
    private String message;
}
