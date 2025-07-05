package wavelog.wavelog.domain.like.application;

import wavelog.wavelog.domain.like.dto.AddRequest;
import wavelog.wavelog.domain.like.dto.AddResponse;
import wavelog.wavelog.domain.like.dto.DeleteRequest;
import wavelog.wavelog.domain.like.dto.DeleteResponse;

public interface LikeService {
    AddResponse add(AddRequest request, Long memberId);

    DeleteResponse delete(DeleteRequest request);
}
