package wavelog.wavelog.domain.diaryHashtag.domain.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DiaryHashtagId implements Serializable {

    private Long diary;
    private Long hashtag;

    public DiaryHashtagId() {}

    public DiaryHashtagId(Long diary, Long hashtag) {
        this.diary = diary;
        this.hashtag = hashtag;
    }

    public Long getDiary() {
        return diary;
    }

    public void setDiary(Long diary) {
        this.diary = diary;
    }

    public Long getHashtag() {
        return hashtag;
    }

    public void setHashtag(Long hashtag) {
        this.hashtag = hashtag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiaryHashtagId)) return false;
        DiaryHashtagId that = (DiaryHashtagId) o;
        return Objects.equals(diary, that.diary) &&
                Objects.equals(hashtag, that.hashtag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diary, hashtag);
    }
}

