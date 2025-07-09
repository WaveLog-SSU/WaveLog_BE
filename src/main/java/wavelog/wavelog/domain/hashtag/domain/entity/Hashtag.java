package wavelog.wavelog.domain.hashtag.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.domain.diaryHashtag.domain.entity.DiaryHashtag;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column
    private String tag;

    // ManyToMany 매핑 수정
//    @ManyToMany(mappedBy = "hashtags")
    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<DiaryHashtag> diaryHashtags = new ArrayList<>();

    @Builder
    public Hashtag(String tag) {
        this.tag = tag;
    }

    public void update(String tag) {
        this.tag = tag;

    }

}
