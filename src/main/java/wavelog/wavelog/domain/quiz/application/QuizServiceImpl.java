package wavelog.wavelog.domain.quiz.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.diary.domain.repository.DiaryRepository;
import wavelog.wavelog.domain.quiz.dto.QuizResponse;


import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final DiaryRepository diaryRepo;
    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper;

    @Override
    public List<QuizResponse.Question> generateQuiz(
            Long memberId
    ) throws JsonProcessingException {
        // 회원 다이어리 모두 조회
        List<Diary> diaries = diaryRepo.findAllByMember_Id(memberId);

        // 다이어리가 없으면 빈 리스트 반환
        if (diaries.isEmpty()) {
            return Collections.emptyList();
        }
        // prompt 조합
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < diaries.size(); i++) {
            Diary d = diaries.get(i);
            sb.append("[Diary ").append(i+1).append("]\n")
                    .append("Code:\n").append(d.getCode()).append("\n")
                    .append("Content:\n").append(d.getContent()).append("\n\n");
        }
        String prompt = """
            아래 %d개의 다이어리를 보고, **10개의 객관식 4지선다 문제**를 JSON 배열로 생성하세요.
            각 문제는 {"prompt":"…","options":["…","…","…","…"],"answerIndex":0} 형태입니다.

            %s

            **반드시 JSON만** 응답해 주세요.
            """.formatted(diaries.size(), sb);

        // LLM 호출
        ChatCompletionRequest req = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("system", "You are a helpful quiz generator."),
                        new ChatMessage("user", prompt)
                ))
                .temperature(0.2)
                .maxTokens(10 * 100)
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(req);

        // JSON 문자열만 꺼내기
        String json = result.getChoices().get(0).getMessage().getContent();

        // JSON 파싱 & 반환
        return objectMapper.readValue(
                json,
                new TypeReference<List<QuizResponse.Question>>() {}
        );
    }
}

