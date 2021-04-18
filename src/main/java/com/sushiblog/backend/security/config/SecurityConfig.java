package com.sushiblog.backend.security.config;


import com.sushiblog.backend.security.jwt.JwtConfigure;
import com.sushiblog.backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable() //웹사이트 취약점 공격
                .cors().and()
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers("/sushi/account").permitAll()
                .antMatchers("/sushi/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/blog/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/blog/details/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/category/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/blog/file/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigure(jwtTokenProvider));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
