package wavelog.wavelog.domain.quiz.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wavelog.wavelog.domain.quiz.application.QuizService;
import wavelog.wavelog.domain.quiz.dto.QuizResponse;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("")
    public ResponseEntity<QuizResponse> createQuiz(Authentication authentication)
            throws JsonProcessingException {
        Long memberId = Long.valueOf(authentication.getName());
        List<QuizResponse.Question> questions =
                quizService.generateQuiz(memberId);

        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new QuizResponse(questions));
    }
}
