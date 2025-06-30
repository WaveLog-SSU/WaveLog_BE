package wavelog.wavelog.domain.member.application;

import wavelog.wavelog.domain.member.dto.SignUpRequest;
import wavelog.wavelog.domain.member.dto.SignUpResponse;

public interface MemberService {
    SignUpResponse signUp(SignUpRequest request);
}
