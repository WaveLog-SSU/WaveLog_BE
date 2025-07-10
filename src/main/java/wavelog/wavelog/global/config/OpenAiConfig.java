package wavelog.wavelog.global.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;


@Configuration
public class OpenAiConfig {
    @Value("${openai.api-key}")
    private String apiKey;

    @Bean
    public OpenAiService openAiService() {
        Duration timeout = Duration.ofSeconds(60);
        return new OpenAiService(apiKey, timeout);
    }
}