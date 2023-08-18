package com.vn.aptech.smartphone.config;

import com.vn.aptech.smartphone.security.CustomUnauthorizedEntryPoint;
import com.vn.aptech.smartphone.security.UserDetailsCustom;
import com.vn.aptech.smartphone.security.UserDetailsServiceCustom;
import com.vn.aptech.smartphone.security.access.AccessAuthenticationFilter;
import com.vn.aptech.smartphone.security.refresh.RefreshAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceCustom userDetailsServiceCustom;
    @Bean
    public SecurityFilterChain authorizeFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName("continue");
        http
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(cache -> cache.requestCache(requestCache))
                .anonymous(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                //https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html
                .logout(AbstractHttpConfigurer::disable)
                //.securityContext((securityContext) -> securityContext.securityContextRepository(securityContextRepository()))
                .addFilterBefore(context.getBean(AccessAuthenticationFilter.class), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(context.getBean(RefreshAuthenticationFilter.class), AccessAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedHandler())
                        .accessDeniedHandler(accessDeniedHandler())
                );

        // @formatter:on
        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of("*"));
//        config.setAllowedOriginPatterns(List.of("*"));
//        config.setAllowedHeaders(List.of("Content-Type", "X-Frame-Options", "X-XSS-Protection",
//                "X-Content-Type-Options", "Strict-Transport-Security", "Authorization"));
//        config.setAllowedMethods(List.of("OPTIONS", "GET", "POST", "PUT", "DELETE"));
//        config.setExposedHeaders(List.of("ERROR_CODE"));
//        config.setAllowCredentials(false);
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }


    @Bean
    @ConditionalOnMissingBean(DaoAuthenticationProvider.class)
    public DaoAuthenticationProvider authProvider(UserDetailsServiceCustom userDetailsServiceCustom) {
        CachingUserDetailsService cachingUserService = new CachingUserDetailsService(userDetailsServiceCustom);
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceCustom);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http,
                                                           UserDetailsService userDetailsService,
                                                           AuthenticationProvider... providers) throws Exception {
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        for (AuthenticationProvider provider : providers) {
            sharedObject.authenticationProvider(provider);
        }
        return sharedObject.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String generatedPassword = passwordEncoder().encode("password");
        return new InMemoryUserDetailsManager(User.withUsername("user@aptech.com")
                .password(generatedPassword).roles("USER").build());
    }


//    @Bean
//    public MethodSecurityExpressionHandler expressionHandler() {
//        var expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        expressionHandler.setPermissionEvaluator(new ResourceOwnerEvaluator());
//        return expressionHandler;
//    }
@Bean
public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
}

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return new CustomUnauthorizedEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

}
