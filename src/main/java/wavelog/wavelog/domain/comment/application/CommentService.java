package wavelog.wavelog.domain.comment.application;

import wavelog.wavelog.domain.comment.dto.CreateCommentRequest;
import wavelog.wavelog.domain.comment.dto.CreateCommentResponse;
import wavelog.wavelog.domain.comment.dto.GetCommentResponse;
import wavelog.wavelog.domain.comment.dto.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    CreateCommentResponse createComment(CreateCommentRequest request, Long memberId, Long diaryId);
    List<GetCommentResponse> getComment(Long diaryId);
    void updateComment(UpdateCommentRequest request, Long memberId, Long commentId);
    void deleteComment(Long commentId, Long memberId);
}
