package hello.postservice.config.auth;

import hello.postservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers((headers) -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher("/"), antMatcher("/css/**"), antMatcher("/images/**"), antMatcher("/js/**"), antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/**")).hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                .logout((logout) -> logout
                                .logoutSuccessUrl("/"))
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfo) -> userInfo
                                        .userService(customOAuth2UserService))
                );

        return http.build();
    }
}
