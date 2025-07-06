package wavelog.wavelog.domain.like.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.like.application.LikeService;
import wavelog.wavelog.domain.like.dto.AddRequest;
import wavelog.wavelog.domain.like.dto.AddResponse;
import wavelog.wavelog.domain.like.dto.DeleteRequest;
import wavelog.wavelog.domain.like.dto.DeleteResponse;
import wavelog.wavelog.global.security.CustomUserDetails;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/add")
    public ResponseEntity<AddResponse> add(@RequestBody AddRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        AddResponse response = likeService.add(request, memberId);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponse> delete(@RequestBody DeleteRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        DeleteResponse response = likeService.delete(request, memberId);
        return ResponseEntity.ok(response);
    }
}
