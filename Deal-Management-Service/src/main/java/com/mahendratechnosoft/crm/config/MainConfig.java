package com.mahendratechnosoft.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mahendratechnosoft.crm.dto.ErrorMessage;
import com.mahendratechnosoft.crm.security.JwtFilter;

@Configuration
public class MainConfig {

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public MainConfig(JwtFilter jwtFilter, CustomAccessDeniedHandler accessDeniedHandler, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailServiceImp();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                 http.cors()  //enabel cros
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**","/lead/**").hasRole("ADMIN")
                .requestMatchers("/employee/**").hasRole("EMP")
                .requestMatchers("/super/**").hasRole("SUPERADMIN")

                .requestMatchers("/signin", "/send_otp", "/verify_otp", "/change_password","/register").permitAll()

                .requestMatchers("/signin", "/send_otp", "/verify_otp", "/change_password","/register","/registerData").permitAll()

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler) // Register custom AccessDeniedHandler
                .authenticationEntryPoint(authenticationEntryPoint); // Register custom AuthenticationEntryPoint

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            ErrorMessage errorResponse = new ErrorMessage(
                    "Access Denied",
                    accessDeniedException,
                    HttpStatus.FORBIDDEN
            );
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(errorResponse.toString());
        };
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // Allow frontend
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, etc.)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

