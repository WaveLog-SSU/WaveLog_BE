package wavelog.wavelog.domain.comment.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wavelog.wavelog.domain.comment.application.CommentService;
import wavelog.wavelog.domain.comment.dto.CommentRequest;
import wavelog.wavelog.domain.comment.dto.CommentResponse;
import wavelog.wavelog.global.security.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/diaies/{diary_id}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsServiceImpl userDetails
    ) {
        Long memberId = userDetails.getId();
        CommentResponse response = commentService.createComment(request, memberId);
        return ResponseEntity
                .status(201)
                .body(response);
    }
}