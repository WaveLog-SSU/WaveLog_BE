package wavelog.wavelog.domain.hashtag.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.hashtag.dto.*;
import wavelog.wavelog.domain.hashtag.application.HashtagService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries/{diary_id}/hashtags")
public class HashtagController {
    private final HashtagService hashtagService;

    @PostMapping
    public ResponseEntity<CreateResponse> create(@PathVariable("diary_id") Long diaryId, @RequestBody CreateRequest request) {
        CreateResponse response = hashtagService.create(diaryId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{hashtag_id}")
    public ResponseEntity<ViewResponse> view(@PathVariable("diary_id") Long diaryId, @PathVariable("hashtag_id") Long id) {
        ViewResponse response = hashtagService.view(diaryId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{hashtag_id}")
    public ResponseEntity<UpdateResponse> update(@PathVariable("diary_id") Long diaryId, @PathVariable("hashtag_id") Long id, @RequestBody UpdateRequest request) {
        UpdateResponse response = hashtagService.update(diaryId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{hashtag_id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable("diary_id") Long diaryId, @PathVariable("hashtag_id") Long id) {
        DeleteResponse response = hashtagService.delete(diaryId, id);
        return ResponseEntity.ok(response);
    }
}
