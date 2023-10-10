package com.example.newsapi.configuration.security;

import com.example.newsapi.configuration.security.jwt.JWTTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JWTTokenFilter jwtTokenFilter;

    private static final String LOGIN_ENDPOINT = "/api/user/auth/**";
    private static final String REG_ENDPOINT = "/api/user";
    private static final String ADMIN_ENDPOINT = "/**/admin/**";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String SWAGGER_ENDPOINT = "/swagger-ui/**";
    private static final String VERSION_ENDPOINT = "/v3/api-docs/**";
    private static final String OPEN_API_ENDPOINT = "/javainuse-openapi/**";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_ENDPOINT, VERSION_ENDPOINT, OPEN_API_ENDPOINT);
         }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(REG_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("lisitsaaa")
                .password(passwordEncoder().encode("ADMIN"))
                .authorities("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}