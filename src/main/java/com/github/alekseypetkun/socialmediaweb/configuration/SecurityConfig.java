package com.github.alekseypetkun.socialmediaweb.configuration;


import com.github.alekseypetkun.socialmediaweb.security.filter.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-doc/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/api/auth/login",
            "/api/auth/register"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests(
                        (authorization) ->
                        {
                            authorization
                                    .requestMatchers(AUTH_WHITELIST).permitAll()
                                    .anyRequest().authenticated()
                                    .and()
                                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                        }
                ).build();
    }
}
