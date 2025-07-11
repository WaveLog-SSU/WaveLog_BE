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

    @Query(
            value =
                    "SELECT COUNT(DISTINCT DATE(created_at)) " +
                    "FROM diary " +
                    "WHERE member_id = :memberId " +
                    "AND YEAR(created_at) = :year " +
                    "AND MONTH(created_at) = :month",
            nativeQuery = true
    )
    long countDistinctByMemberAndYearAndMonth(
            @Param("memberId") Long memberId,
            @Param("year")     int year,
            @Param("month")    int month
    );

    List<Diary> findByTitleContainingIgnoreCase(String title);

}
