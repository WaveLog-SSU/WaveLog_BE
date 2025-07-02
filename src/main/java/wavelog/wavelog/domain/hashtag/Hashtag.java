package wavelog.wavelog.domain.hashtag;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

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

    @Builder
    public Hashtag(String tag) {
        this.tag = tag;
    }

}
