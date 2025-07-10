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
import wavelog.wavelog.global.common.domain.entity.JsonUtils;


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
아래 %d개의 다이어리를 보고, **총 10개의 객관식(4지선다)** 문제를 “절대” 코드펜스(```)나 백틱(`) 없이, “순수 JSON 배열”로만 생성해주세요.
**Markdown, 주석, 백틱, 코드펜스 일체 금지!**  
각 문제 객체는 다음 필드를 반드시 포함해야 합니다:
  {
    "prompt": "문제 지문",
    "options": ["선지1","선지2","선지3","선지4"],
    "answerIndex": 정답 인덱스(0~3)
  }

- **options** 중 하나는 정답, 나머지 3개는 “그럴듯하지만 틀린” 오답이어야 합니다.  
- **answerIndex** 값은 0,1,2,3이 골고루 분포되도록(각 인덱스가 최소 한 번씩 나눠 갖도록) 배분해주세요.  
- **절대** Markdown, 백틱(`), 코드펜스(```), 주석 등을 포함하지 마시고, **오직** JSON 문자만 출력해주세요.

예시  
[
  {
    "prompt": "Java로 콘솔에 ‘Hello, World!’를 출력하는 코드는?",
    "options": [
      "System.out.println(\\"Hello, World!\\");",
      "Console.WriteLine(\\"Hello, World!\\");",
      "print(\\"Hello, World!\\")",
      "echo \\"Hello, World!\\";"
    ],
    "answerIndex": 0
  },
  {
    "prompt": "Python에서 리스트 컴프리헨션으로 제곱 리스트를 만드는 방법은?",
    "options": [
      "squares = [x*x for x in range(10)]",
      "squares = [x**2 for x in range(10)]",
      "squares = list(map(lambda x: x*x, range(10)))",
      "[x**2 for x in range(10)]"
    ],
    "answerIndex": 2
  }
]

%s
""".formatted(diaries.size(), sb);






        // LLM 호출
        ChatCompletionRequest req = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("system", "You are a helpful quiz generator."),
                        new ChatMessage("user", prompt)
                ))
                .temperature(0.3)
                .maxTokens(3000)
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(req);
        String raw = result.getChoices().get(0).getMessage().getContent();
        System.out.println("GPT raw response >>>\n" + raw);
        String json = JsonUtils.extractJsonArray(raw);
        System.out.println("Extracted JSON >>>\n" + json);

// 이제 안전하게 파싱
        return objectMapper.readValue(
                json,
                new TypeReference<List<QuizResponse.Question>>() {}
        );
    }
}

