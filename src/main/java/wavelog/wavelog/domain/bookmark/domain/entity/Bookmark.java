package wavelog.wavelog.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;


}
