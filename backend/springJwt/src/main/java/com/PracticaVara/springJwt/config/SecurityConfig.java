package com.PracticaVara.springJwt.config;

import com.PracticaVara.springJwt.filter.JwtAuthenticationFilter;
import com.PracticaVara.springJwt.interceptors.BearerTokenInterceptor;
import com.PracticaVara.springJwt.interceptors.BearerTokenWrapper;
import com.PracticaVara.springJwt.service.AccountServices.UserDetailsServiceImp;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                        req.requestMatchers("/api/Authentification/login/**",
                                "/api/Authentification/register/**",
                                "/api/Account/forgot-password/**",
                                "/api/Account/reset-password/**",
                                "/api/Account/confirm-email").permitAll();
                        req.requestMatchers("/api/Account/update-profile", "/api/Account/get-account").authenticated();
                        req.requestMatchers("/api/Authentification/refresh-page").authenticated();
                        req.requestMatchers("/ads-imgs/**").permitAll();
                        req.requestMatchers("/category-imgs/**").permitAll();
                        req.requestMatchers("/api/Announcements/get-all-ads", "/api/Announcements/get-ad-by-url/**", "/api/Announcements/search-for-ads", "/api/Announcements/get-ads-by-category/**", "/api/Announcements/get-ads-for-user/**").permitAll();
                        req.requestMatchers("/api/Announcements/**").authenticated();
                        req.requestMatchers("/api/Announcements-management/**").hasAnyRole("ADMIN", "MODERATOR");
                        req.requestMatchers("/api/UserManagement/**").hasRole("ADMIN");
                        req.requestMatchers("/api/Reports/**").authenticated();
                        req.requestMatchers("/api/Reports-management/**").hasRole("ADMIN");
                        req.requestMatchers("/api/Categories/get-all-categories").permitAll();
                        req.requestMatchers("/api/Categories/**").hasRole("ADMIN");
                        req.requestMatchers("/api/Suspended-accounts/**").hasRole("ADMIN");
                        req.requestMatchers("/message", "/app", "/ws/**").permitAll();
                        req.requestMatchers("/api/Chatrooms/**").authenticated();
                        req.requestMatchers("/api/DashboardDetails/**").hasAnyRole("ADMIN", "MODERATOR");
                        req.requestMatchers("/api/Wishlist/**").authenticated();
                        req.requestMatchers("/api/LogHistory/**").hasAnyRole("ADMIN");
                        req.anyRequest().denyAll();
                    }
                )
                .userDetailsService(userDetailsServiceImp)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("/public/");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerTokenInterceptor());
    }

    @Bean
    public BearerTokenInterceptor bearerTokenInterceptor() {
        return new BearerTokenInterceptor(bearerTokenWrapper());
    }

    @Bean
    public BearerTokenWrapper bearerTokenWrapper() {
        return new BearerTokenWrapper();
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]");
            }
        });
        return factory;
    }
}
