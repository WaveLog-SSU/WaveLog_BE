package wavelog.wavelog.domain.member.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String wavelogId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column
    private String profileImageUrl;

    @Column
    private String introIndex;

    @Builder
    public Member(String wavelogId, String name, String password, String nickname, String profileImageUrl, String introIndex) {
        this.wavelogId = wavelogId;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introIndex = introIndex;
    }
}
