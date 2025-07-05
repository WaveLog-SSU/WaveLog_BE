package wavelog.wavelog.domain.like.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.domain.diary.domain.entity.Diary;
import wavelog.wavelog.domain.member.domain.entity.Member;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    // user->member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public void add(Diary diary, Member member) {
        this.diary = diary;
        this.member = member;
    }

}
