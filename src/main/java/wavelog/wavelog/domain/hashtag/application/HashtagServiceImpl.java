package wavelog.wavelog.domain.hashtag.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.hashtag.dto.*;
import wavelog.wavelog.domain.hashtag.domain.entity.Hashtag;
import wavelog.wavelog.domain.hashtag.domain.repository.HashtagRepository;


@Service
@RequiredArgsConstructor
@Transactional
public class HashtagServiceImpl implements HashtagService {
    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    @Override
    public CreateResponse create(Long diaryId, CreateRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));
        Hashtag hashtag = hashtagRepository.findByTag(request.getTag())
                .orElseGet(() -> hashtagRepository.save(
                        Hashtag.builder()
                                .tag(request.getTag())
                                .build()
                ));

        // 중복 add 방지
        if(!diary.getHashtags().contains(hashtag)) {
            diary.getHashtags().add(hashtag);
            hashtag.getDiaries().add(diary);
        }


        //  DTO 반환
        return CreateResponse.builder()
                .id(hashtag.getId())
                .tag(hashtag.getTag())
                .build();
    }

    @Override
    public ViewResponse view(Long diaryId, Long id) {
        diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));

        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 해시태그입니다."));

        return ViewResponse.builder()
                .id(hashtag.getId())
                .tag(hashtag.getTag())
                .build();
    }

    @Override
    public UpdateResponse update(Long diaryId, Long id, UpdateRequest request) {
        diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));
        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 해시태그입니다."));

        hashtag.update(request.getTag());

        return UpdateResponse.builder()
                .id(hashtag.getId())
                .message("해시태그가 업데이트되었습니다.")
                .build();
    }

    @Override
    public DeleteResponse delete(Long diaryId, Long id) {
        diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));

        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 해시태그입니다."));

        hashtagRepository.delete(hashtag);


        return DeleteResponse.builder()
                .id(id)
                .message("해시태그가 삭제되었습니다.")
                .build();
    }
}
