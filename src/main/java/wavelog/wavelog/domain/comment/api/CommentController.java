package wavelog.wavelog.domain.comment.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.comment.application.CommentService;
import wavelog.wavelog.domain.comment.dto.CreateCommentRequest;
import wavelog.wavelog.domain.comment.dto.CreateCommentResponse;
import wavelog.wavelog.domain.comment.dto.GetCommentResponse;
import wavelog.wavelog.domain.comment.dto.UpdateCommentRequest;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/diaries/{diary_id}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @Valid @RequestBody CreateCommentRequest request,
            Authentication authentication,
            @PathVariable(name="diary_id") Long diaryId
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        CreateCommentResponse response = commentService.createComment(request, memberId, diaryId);
        return ResponseEntity
                .status(201)
                .body(response);
    }

    @GetMapping("/diaries/{diary_id}/comments")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable(name="diary_id") Long diaryId
    ) {
        List<GetCommentResponse> response = commentService.getComment(diaryId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("comment_id") Long commentId,
            @Valid @RequestBody UpdateCommentRequest request,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        commentService.updateComment(request, memberId, commentId);
        return ResponseEntity.noContent().build();
    }


}
