package hu.fourig.demo.config;

import hu.fourig.demo.data.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private static final String[] AUTH_WHITELIST = {
            "/api/auth/login",
            "/api/auth/register"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()  // ✅ Swagger engedélyezése
                        .requestMatchers(AUTH_WHITELIST).permitAll()  // ✅ AuthController végpontok engedélyezése
                        /*.requestMatchers(HttpMethod.GET, "/api/partners").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/addresses/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/partners/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/partners/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/partners/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/addresses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/addresses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/addresses/**").hasRole("ADMIN")*/
                        .requestMatchers(HttpMethod.GET, "/api/partners").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/addresses/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/partners/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/partners/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/partners/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/addresses/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/addresses/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/addresses/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
