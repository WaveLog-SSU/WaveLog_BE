package wavelog.wavelog.global.common.domain.entity;

public class JsonUtils {
    public static String extractJsonArray(String raw) {
        // 1) ```json ... ``` 제거
        String noFences = raw.replaceAll("(?s)```json.*?```", "")
                .replaceAll("(?s)```.*?```", "");

        // 2) 첫 '[' 부터 마지막 ']' 까지 잘라내기
        int start = noFences.indexOf('[');
        int end   = noFences.lastIndexOf(']');
        if (start != -1 && end != -1 && end > start) {
            return noFences.substring(start, end + 1);
        }
        // 배열이 안 보이면 원본 반환
        return raw;
    }
}
