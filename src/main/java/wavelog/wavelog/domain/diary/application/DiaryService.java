package wavelog.wavelog.domain.diary.application;

import org.hibernate.sql.Update;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.dto.*;

import java.util.List;

public interface DiaryService {

    CreateResponse create(CreateRequest request);

    DeleteResponse delete(Long diaryId);

    UpdateResponse update(Long diaryId, UpdateRequest request);

    ViewResponse view(Long diaryId);

    List<ViewResponse> listByDateAndMember(String date, Long memberId);


    //void deleteHashtagFromDiary(Long diaryId, Long hashtagId);

    }
