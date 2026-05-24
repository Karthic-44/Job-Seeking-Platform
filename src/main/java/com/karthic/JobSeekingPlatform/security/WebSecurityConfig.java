package com.karthic.JobSeekingPlatform.security;


import com.karthic.JobSeekingPlatform.model.AppRole;
import com.karthic.JobSeekingPlatform.model.Role;
import com.karthic.JobSeekingPlatform.model.Users;
import com.karthic.JobSeekingPlatform.repositories.RoleRepository;
import com.karthic.JobSeekingPlatform.repositories.UserRepository;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import com.karthic.JobSeekingPlatform.security.jwt.AuthEntryPointJwt;
import com.karthic.JobSeekingPlatform.security.jwt.AuthTokenFilter;
import com.karthic.JobSeekingPlatform.security.services.UserDetailsServiceImpl;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {})  
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/recruiter/**").hasAnyRole("ADMIN","RECRUITER")
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(
                frameOptions -> frameOptions.sameOrigin()));
        
        //HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"));
    }


  @Bean
public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
        // Retrieve or create roles
        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

        Role recruiterRole = roleRepository.findByRoleName(AppRole.ROLE_RECRUITER)
                .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_RECRUITER)));

        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

        // Create users with roles in one go
        // if (!userRepository.existsByUserName("user1")) {
        //     Users user1 = new Users("user1", "user1@example.com", passwordEncoder.encode("password1"));
        //     user1.setRoles(Set.of(userRole));
        //     userRepository.save(user1);
        // }

        // if (!userRepository.existsByUserName("recruiter1")) {
        //     Users recruiter1 = new Users("recruiter1", "recruiter1@example.com", passwordEncoder.encode("password2"));
        //     recruiter1.setRoles(Set.of(recruiterRole));
        //     userRepository.save(recruiter1);
        // }

        if (!userRepository.existsByUserName("admin")) {
            Users admin = new Users();
            admin.setUserName("admin");
            admin.setEmail("admin@example.com"); 
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(userRole, recruiterRole, adminRole));
            admin.setUserPhoneNumber("1234567890");
            userRepository.save(admin);
        }

          if (!userRepository.existsByUserName("user")) {
            Users user = new Users();
            user.setUserName("user");
            user.setEmail("user@example.com"); 
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(userRole));
            user.setUserPhoneNumber("1234567890");
            userRepository.save(user);
        }

          if (!userRepository.existsByUserName("recruiter")) {
            Users recruiter = new Users();
            recruiter.setUserName("recruiter");
            recruiter.setEmail("recruiter@example.com"); 
            recruiter.setPassword(passwordEncoder.encode("recruiter"));
            recruiter.setRoles(Set.of(recruiterRole));
            recruiter.setUserPhoneNumber("1234567890");
            userRepository.save(recruiter);
        }
     };
}

}