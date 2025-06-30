package wavelog.wavelog.domain.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wavelog.wavelog.domain.member.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
