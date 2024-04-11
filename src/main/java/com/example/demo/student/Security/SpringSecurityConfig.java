package com.example.demo.student.Security;

import com.example.demo.student.Security.Filter.JwtAuthenticationFilter;
import com.example.demo.student.Security.Filter.JwtAuthenticationFilterTeacher;
import com.example.demo.student.Security.Filter.JwtValidationFilter;
import com.example.demo.student.Security.Filter.JwtValidationFilterTeacher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authz) -> authz
                        //maestro ->
                        .requestMatchers(HttpMethod.GET, "api/v1/teacher").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/v1/teacher/listgrupos").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/teacher/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/v1/teacher/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/v1/teacher/desactivar/{idMaestro}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/v1/teacher/{id}/grupo/{idGrupo}").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/teacher/creargrupo/maestro/{idMaestro}/materia/{idMateria}").permitAll()
                        //estudiante ->
                        .requestMatchers(HttpMethod.GET, "/api/v1/student").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/student/listgrupos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/student/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/v1/student/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/v1/student/{Id}/grupo/{idGrupo}").permitAll()
                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))

                .addFilter(new JwtAuthenticationFilterTeacher(authenticationManager()))
                .addFilter(new JwtValidationFilterTeacher(authenticationManager()))
                .csrf(AbstractHttpConfigurer::disable)
                //.csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-type"));
        config.setAllowedHeaders(Arrays.asList("contentType", "ngrok-skip-browser-warning"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Accept"));
        config.setAllowPrivateNetwork(true);
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new
                CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
