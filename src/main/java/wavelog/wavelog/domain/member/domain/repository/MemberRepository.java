package wavelog.wavelog.domain.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByWavelogId(String wavelogId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByWavelogId(String wavelogId);

}

// 구현체 생성 안해도 됨 -> JpaRepository가 기본적인 CRUD 기능 상속해줌