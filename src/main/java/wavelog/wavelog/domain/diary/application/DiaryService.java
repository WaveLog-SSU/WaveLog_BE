package wavelog.wavelog.domain.diary.application;

import org.hibernate.sql.Update;
import wavelog.wavelog.domain.diary.dto.*;

public interface DiaryService {
    CreateResponse create(CreateRequest request);

    DeleteResponse delete(DeleteRequest request);

    UpdateResponse update(UpdateRequest request);

    ViewResponse view(ViewRequest request);

    //void deleteHashtagFromDiary(Long diaryId, Long hashtagId);

    }
