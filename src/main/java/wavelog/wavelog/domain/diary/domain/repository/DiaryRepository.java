package wavelog.wavelog.domain.diary.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wavelog.wavelog.domain.diary.domain.entity.Diary;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE FUNCTION('DATE', d.createdAt) = :date AND d.member.id = :memberId")
    List<Diary> findByCreatedAtDateAndMemberId(@Param("date") String date, @Param("memberId") Long memberId);

    List<Diary> findByMemberId(Long memberId);

}
