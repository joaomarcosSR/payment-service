package com.payment.service.infrastructure.configuration;

import com.payment.service.application.consumer.service.ConsumerService;
import com.payment.service.application.context.ConsumerContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final ConsumerService consumerService;

    public SecurityConfig(final ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/payments*").authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .addFilterBefore(new TokenAuthenticationFilter(this.consumerService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    static class TokenAuthenticationFilter extends OncePerRequestFilter {
        private final ConsumerService consumerService;

        public TokenAuthenticationFilter(final ConsumerService consumerService) {
            this.consumerService = consumerService;
        }

        @Override
        protected void doFilterInternal(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final FilterChain filterChain) throws ServletException, IOException {

            Optional.ofNullable(extractTokenFromRequest(request))
                    .flatMap(this.consumerService::getConsumer)
                    .ifPresent(consumer ->
                    {
                        final PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken("user", null, null);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        ConsumerContext.setConsumer(consumer);
                    });

            try {
                filterChain.doFilter(request, response);
            } finally {
                ConsumerContext.clear();
            }
        }

        private String extractTokenFromRequest(final HttpServletRequest request) {
            final String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) return bearerToken.substring(7);
            return null;
        }
    }
}
