package wavelog.wavelog.domain.bookmark.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<AddResponse> add(@RequestBody AddRequest request, Authentication authentication) {
        Long memberId = Long.valueOf(authentication.getName());
        AddResponse response = bookmarkService.add(request, memberId);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{bookmark_id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable("bookmark_id") Long id, Authentication authentication) {
        Long memberId = Long.valueOf(authentication.getName());
        DeleteResponse response = bookmarkService.delete(id, memberId);
        return ResponseEntity.ok(response);
    }
}
