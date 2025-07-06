package wavelog.wavelog.domain.comment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.comment.domain.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiary_IdOrderByCreatedAtAsc(Long diaryId);
}
