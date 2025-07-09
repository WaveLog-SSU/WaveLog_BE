package wavelog.wavelog.domain.hashtag.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.diaryHashtag.domain.entity.DiaryHashtag;
import wavelog.wavelog.domain.diaryHashtag.domain.repository.DiaryHashtagRepository;
import wavelog.wavelog.domain.hashtag.dto.*;
import wavelog.wavelog.domain.hashtag.domain.entity.Hashtag;
import wavelog.wavelog.domain.hashtag.domain.repository.HashtagRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagServiceImpl implements HashtagService {

    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;

    @Override
    public CreateResponse create(Long diaryId, CreateRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));
        Hashtag hashtag = hashtagRepository.findByTag(request.getTag())
                .orElseGet(() -> hashtagRepository.save(
                        Hashtag.builder()
                                .tag(request.getTag())
                                .build()
                ));

        // 현재 연결 유무 확인
        boolean alreadyLinked = diaryHashtagRepository
                .findByDiaryIdAndHashtagId(diary.getId(), hashtag.getId())
                .isPresent();

        // 중복 add 방지
        if(!alreadyLinked) {
            DiaryHashtag diaryHashtag = DiaryHashtag.builder()
                    .diary(diary)
                    .hashtag(hashtag)
                    .build();
            diaryHashtagRepository.save(diaryHashtag);
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));

        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 해시태그입니다."));

        if (!diaryHashtagRepository.findByDiaryIdAndHashtagId(diaryId, id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "연결되지 않은 해시태그입니다.");
        }

        return ViewResponse.builder()
                .id(hashtag.getId())
                .tag(hashtag.getTag())
                .build();
    }

    @Override
    public UpdateResponse update(Long diaryId, Long id, UpdateRequest request) {
        diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 다이어리입니다."));
        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 해시태그입니다."));

        hashtag.update(request.getTag());

        return UpdateResponse.builder()
                .id(hashtag.getId())
                .message("해시태그가 업데이트되었습니다.")
                .build();
    }

    @Override
    public DeleteResponse delete(Long diaryId, Long id) {
        diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리입니다."));

        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 해시태그입니다."));

        Optional<DiaryHashtag> relation = diaryHashtagRepository.findByDiaryIdAndHashtagId(diaryId, id);

        if(relation.isPresent()) {
            diaryHashtagRepository.delete(relation.get());
        }


        // 해시태그가 더 이상 어떤 다이어리와도 연결 안 되어 있으면 삭제
        boolean hashtagLinked = diaryHashtagRepository.existsByHashtagId(id);
        if (!hashtagLinked) {
            hashtagRepository.deleteById(id);
        }

        return DeleteResponse.builder()
                .id(id)
                .message("다이어리와 해시태그의 연결이 끊어졌습니다.")
                .build();
    }
}
