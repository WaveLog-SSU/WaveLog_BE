package wavelog.wavelog.domain.diary.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.diary.dto.*;
import wavelog.wavelog.domain.diary.application.DiaryService;


@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> create(@RequestBody CreateRequest request) {
        CreateResponse response = diaryService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/view")
    public ResponseEntity<ViewResponse> view(@RequestBody ViewRequest request) {
        ViewResponse response = diaryService.view(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponse> update(@RequestBody UpdateRequest request) {
        UpdateResponse response = diaryService.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponse> delete(@RequestBody DeleteRequest request) {
        DeleteResponse response = diaryService.delete(request);
        return ResponseEntity.ok(response);
    }

}
