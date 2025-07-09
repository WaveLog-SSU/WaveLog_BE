package wavelog.wavelog.domain.diary.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.diary.dto.*;
import wavelog.wavelog.domain.diary.application.DiaryService;

import java.util.List;


@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping
    public ResponseEntity<List<ViewResponse>> list(@RequestParam(required = false) String date) {
        List<ViewResponse> responseList = diaryService.list(date);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@RequestBody CreateRequest request) {
        CreateResponse response = diaryService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{diary_id}")
    public ResponseEntity<ViewResponse> view(@PathVariable("diary_id") Long diaryId) {
        ViewResponse response = diaryService.view(diaryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{diary_id}")
    public ResponseEntity<UpdateResponse> update(@PathVariable("diary_id") Long diaryId, @RequestBody UpdateRequest request) {
        UpdateResponse response = diaryService.update(diaryId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{diary_id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable("diary_id") Long diaryId) {
        DeleteResponse response = diaryService.delete(diaryId);
        return ResponseEntity.ok(response);
    }

}
