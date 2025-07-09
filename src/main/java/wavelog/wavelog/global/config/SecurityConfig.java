package wavelog.wavelog.global.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wavelog.wavelog.global.jwt.JwtAuthenticationFilter;
import wavelog.wavelog.global.jwt.JwtTokenProvider;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, userDetailsService);

        httpSecurity
                // CSRF 비활성화
        .csrf(csrf -> csrf.disable())
                // CORS 활성화 (WebMvcConfigurer 등에서 미리 허용 origin/메소드 등을 설정해 두어야 동작)
        .cors(withDefaults())
                // HTTP Basic 인증 비활성화
        .httpBasic(HttpBasicConfigurer::disable)

                // 세션 없이 JWT만으로 인증
        .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
                // 엔드포인트별 접근 제어
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/members/register", "/api/members/login").permitAll()
                .anyRequest().authenticated()
        )
                // JWT 필터 등록
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 인증 실패 시 401 처리
        .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                        (req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                )
        );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
