package wavelog.wavelog.domain.like.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.like.domain.entity.Like;
import wavelog.wavelog.domain.member.domain.entity.Member;

import javax.xml.crypto.Data;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByDiary(Diary diary);
    boolean existsByDiaryAndMember(Diary diary, Member member);
}
