package wavelog.wavelog.domain.quiz.dto;

import java.util.List;

public record QuizResponse(
        List<Question> questions
) {
    public static class Question {
        public String prompt;
        public List<String> options;
        public int answerIndex;
    }
}
