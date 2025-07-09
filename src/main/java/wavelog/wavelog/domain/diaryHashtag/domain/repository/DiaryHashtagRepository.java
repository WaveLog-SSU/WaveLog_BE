package wavelog.wavelog.domain.diaryHashtag.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wavelog.wavelog.domain.diaryHashtag.domain.entity.DiaryHashtag;
import wavelog.wavelog.domain.diaryHashtag.domain.entity.DiaryHashtagId;

import java.util.Optional;

public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, DiaryHashtagId> {

    @Query("SELECT dh FROM DiaryHashtag dh WHERE dh.diary.id = :diaryId AND dh.hashtag.id = :hashtagId")
    Optional<DiaryHashtag> findByDiaryIdAndHashtagId(Long diaryId, Long hashtagId);
    boolean existsByHashtagId(Long hashtagId);

}
