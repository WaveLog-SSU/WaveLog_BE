package wavelog.wavelog.domain.diary.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.domain.bookmark.domain.entity.Bookmark;
import wavelog.wavelog.domain.comment.domain.entity.Comment;
import wavelog.wavelog.domain.hashtag.domain.entity.Hashtag;
import wavelog.wavelog.domain.like.domain.entity.Like;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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
    private Integer likeCount = 0;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    // member 없이는 diary를 만들 수 없음

    @ManyToMany
    @JoinTable(
            name = "diary_hashtag",
            joinColumns = @JoinColumn(name = "diary_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    public Diary(String title, String code, String content, Integer likeCount, Integer viewCount, String category, Member member) {
        this.title = title;
        this.code = code;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.category = category;
        this.member = member;
    }

    // DiarySeviceImpl에서 update시 dto의 UpdateRequest의 필드들을 받아오는 용도
    public void update(String title, String code, String content, String category/*, List<Hashtag> hashtags*/) {
        this.title = title;
        this.code = code;
        this.content = content;
        this.category = category;
//        if(hashtags != null)
//            this.hashtags = new ArrayList<>(hashtags);
    }
    // 좋아요 수 증가 감소 메서드
    public void addLikeCount()
    {
        this.likeCount++;
    }

    public void deleteLikeCount() {
        if(this.likeCount > 0)
            this.likeCount--;
    }

}
