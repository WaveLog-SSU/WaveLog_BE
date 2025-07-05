package wavelog.wavelog.domain.diary.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wavelog.wavelog.domain.diary.domain.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
