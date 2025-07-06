package wavelog.wavelog.domain.comment.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.comment.application.CommentService;
import wavelog.wavelog.domain.comment.dto.CommentRequest;
import wavelog.wavelog.domain.comment.dto.CommentResponse;
import wavelog.wavelog.global.security.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/diaries/{diary_id}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @Valid @RequestBody CommentRequest request,
            Authentication authentication,
            @PathVariable(name="diary_id") Long diaryId
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        CommentResponse response = commentService.createComment(request, memberId, diaryId);
        return ResponseEntity
                .status(201)
                .body(response);
    }
}