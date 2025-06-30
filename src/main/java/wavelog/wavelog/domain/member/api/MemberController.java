package wavelog.wavelog.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wavelog.wavelog.domain.member.dto.SignUpRequest;
import wavelog.wavelog.domain.member.dto.SignUpResponse;
import wavelog.wavelog.domain.member.application.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        SignUpResponse response = memberService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)  // 201 Created
                .body(response);
    }

}
