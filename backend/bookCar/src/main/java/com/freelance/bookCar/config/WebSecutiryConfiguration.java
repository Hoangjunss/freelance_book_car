package com.freelance.bookCar.config;


import com.freelance.bookCar.security.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecutiryConfiguration {
@Autowired
@Lazy
private OurUserDetailsService ourUserDetailsService;
@Autowired
private JwtAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) ->auth
                        .requestMatchers("/api/v1/auth/registration","/api/v1/auth/login","/api/v1/auth/refreshtoken").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/hotel-booking").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/hotel").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/ticket").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/tourism").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/tour").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/tourSchedule").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/page/detail").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/page/home").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/page/footer").permitAll()

                        .anyRequest()
                        .authenticated()

                ).httpBasic(Customizer.withDefaults())

                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
        );



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
