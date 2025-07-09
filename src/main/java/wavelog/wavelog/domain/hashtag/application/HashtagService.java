package wavelog.wavelog.domain.hashtag.application;

import wavelog.wavelog.domain.hashtag.dto.*;

public interface HashtagService {
    CreateResponse create(Long diaryId, CreateRequest request);

    ViewResponse view(Long diaryId, Long id);

    UpdateResponse update(Long diaryId, Long id, UpdateRequest request);

    DeleteResponse delete(Long diaryId, Long id);
}
