package wavelog.wavelog.domain.diary.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column
    private String category;

    @Builder
    public Diary(String title, String code, String content, Integer likeCount, Integer viewCount, String category) {
        this.title = title;
        this.code = code;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.category = category;
    }

}
