package com.example.weblabb4.config;

import com.example.weblabb4.config.jwt.JwtFilter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * Configuration annotation для пометки как конфигурационного файла EnableWebSecurity annotation для указания, что
 * данный класс является классом настроек Spring Security extends WebSecurityConfigurerAdapter для настройки системы
 * секюрити и авторизации под свои нужды
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    /**
     * сonfigure(HttpSecurity) для обслуживания URL
     * sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) которая будет управлять сессией юзера
     * в системе спринг секюрити. Так как я буду авторизировать пользователя по токену, мне не нужно создавать и хранить
     * для него сессию. Поэтому я указал STATELESS. antMatchers чтобы указать какие URL адреса будут доступны для
     * определенной роли, а какие нет
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/register", "/auth").permitAll()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("localhost"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * PasswordEncoder для кодировки пароля
     *
     * @return закодированный пароль
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
