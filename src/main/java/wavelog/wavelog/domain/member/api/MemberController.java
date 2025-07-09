package wavelog.wavelog.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wavelog.wavelog.domain.member.dto.*;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{member_id}")
    public ResponseEntity<GetMemberResponse> getMember(@PathVariable("member_id") Long memberId) {
        GetMemberResponse response = memberService.getMember(memberId);
        return ResponseEntity.ok(response);
    }

}
