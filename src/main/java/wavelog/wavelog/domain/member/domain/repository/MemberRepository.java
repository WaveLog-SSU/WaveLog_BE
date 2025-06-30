package wavelog.wavelog.domain.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByWavelogId(String wavelogId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByWavelogId(String wavelogId);

}
