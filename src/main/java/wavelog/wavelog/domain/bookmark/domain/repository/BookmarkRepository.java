package wavelog.wavelog.domain.bookmark.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.bookmark.domain.entity.Bookmark;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.member.domain.entity.Member;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByDiaryAndMember(Diary diary, Member member);
}
