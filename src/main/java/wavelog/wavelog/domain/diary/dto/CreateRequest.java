package wavelog.wavelog.domain.diary.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.domain.member.domain.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRequest {
    private Long memberId;
    private String title;
    private String code;
    private String content;
    private String category;
    private List<String> hashtags;
}

