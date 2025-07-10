package wavelog.wavelog.domain.quiz.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import wavelog.wavelog.domain.quiz.dto.QuizResponse;

import java.util.List;

public interface QuizService {
    public List<QuizResponse.Question> generateQuiz(
            Long memberId
    ) throws JsonProcessingException;
}
