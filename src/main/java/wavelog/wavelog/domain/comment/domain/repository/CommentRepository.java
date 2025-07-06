package wavelog.wavelog.domain.comment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.comment.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
