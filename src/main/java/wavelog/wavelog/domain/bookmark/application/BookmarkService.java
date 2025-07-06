package wavelog.wavelog.domain.bookmark.application;

import wavelog.wavelog.domain.bookmark.dto.AddRequest;
import wavelog.wavelog.domain.bookmark.dto.AddResponse;
import wavelog.wavelog.domain.bookmark.dto.DeleteResponse;

public interface BookmarkService {
    AddResponse add(AddRequest request, Long memberId);

    DeleteResponse delete(Long id, Long memberId);

}
