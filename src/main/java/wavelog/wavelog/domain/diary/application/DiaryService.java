package wavelog.wavelog.domain.diary.application;

import org.hibernate.sql.Update;
import wavelog.wavelog.domain.diary.dto.*;

import java.util.List;

public interface DiaryService {

    List<ViewResponse> list(String date);

    CreateResponse create(CreateRequest request);

    DeleteResponse delete(Long diaryId);

    UpdateResponse update(Long diaryId, UpdateRequest request);

    ViewResponse view(Long diaryId);

    //void deleteHashtagFromDiary(Long diaryId, Long hashtagId);

    }
