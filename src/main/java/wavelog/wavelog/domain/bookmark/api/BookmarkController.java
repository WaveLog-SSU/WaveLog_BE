package wavelog.wavelog.domain.bookmark.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.bookmark.application.BookmarkService;
import wavelog.wavelog.domain.bookmark.dto.AddResponse;
import wavelog.wavelog.domain.bookmark.dto.DeleteResponse;
import wavelog.wavelog.domain.bookmark.dto.AddRequest;
import wavelog.wavelog.global.security.CustomUserDetails;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    @PostMapping
    public ResponseEntity<AddResponse> add(@RequestBody AddRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        AddResponse response = bookmarkService.add(request, memberId);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{bookmark_id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable("bookmark_id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        DeleteResponse response = bookmarkService.delete(id, memberId);
        return ResponseEntity.ok(response);
    }
}
