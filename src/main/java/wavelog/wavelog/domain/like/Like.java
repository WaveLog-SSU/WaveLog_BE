package wavelog.wavelog.domain.like;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wavelog.wavelog.global.common.domain.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

}
