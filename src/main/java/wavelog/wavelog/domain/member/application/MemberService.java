package wavelog.wavelog.domain.member.application;

import wavelog.wavelog.domain.member.dto.SignUpRequest;
import wavelog.wavelog.domain.member.dto.SignUpResponse;
import wavelog.wavelog.domain.member.dto.LoginRequest;
import wavelog.wavelog.domain.member.dto.LoginResponse;

public interface MemberService {

    SignUpResponse signUp(SignUpRequest request);

    LoginResponse login(LoginRequest request);

}
