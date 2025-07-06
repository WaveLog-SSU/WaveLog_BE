package wavelog.wavelog.domain.comment.application;

import wavelog.wavelog.domain.comment.dto.CommentRequest;
import wavelog.wavelog.domain.comment.dto.CommentResponse;

public interface CommentService {
    CommentResponse createComment(CommentRequest request, Long memberId);
}
