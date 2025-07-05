package wavelog.wavelog.domain.diary.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateRequest {
    private Long id;
    private String title;
    private String code;
    private String content;
    private String category;
    //private List<String> hashtags = new ArrayList<>();
}
