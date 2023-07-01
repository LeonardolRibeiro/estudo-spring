package com.leolaia.estudospring.configs;

import com.leolaia.estudospring.security.jwt.JwtConfigurer;
import com.leolaia.estudospring.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    //Define como serão encriptadas as senhas para fazer a comparação com a senha que está no banco
    // para isso implementamos um algoritimo que diz qual será utilizado para fazer o encoding e mais algumas informações
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//
//        //pbkdf2 é mais performatico que o decoder
//        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 8, 18500, PBKDF2WithHmacSHA256);
//        encoders.put("pbkdf2", encoder);
//
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);
//        return passwordEncoder;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "/auth/signin",
                                        "/auth/refresh?**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                .permitAll()//Permite que todos os endPoints acima sejam acessados sem autenticação
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/users").denyAll()
                )
                .cors()
                .and()
                .apply(new JwtConfigurer(tokenProvider))
                .and()
                .build();

    }

}
