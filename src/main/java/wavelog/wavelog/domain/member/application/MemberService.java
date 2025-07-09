package wavelog.wavelog.domain.member.application;

import wavelog.wavelog.domain.member.dto.*;

public interface MemberService {

    SignUpResponse signUp(SignUpRequest request);

    LoginResponse login(LoginRequest request);

    GetMemberResponse getMember(Long memberId);

}
