package wavelog.wavelog.domain.diaryHashtag.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.hashtag.domain.entity.Hashtag;

@Entity
@Getter
@NoArgsConstructor
//
@IdClass(DiaryHashtagId.class)
@Table(name = "diary_hashtag")
public class DiaryHashtag {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Builder
    public DiaryHashtag(Diary diary, Hashtag hashtag) {
        this.diary = diary;
        this.hashtag = hashtag;
    }
}
