package com.laskapi.eshop.productservice;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
@Profile("!test")
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private DiscoveryClient dc;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()).oauth2ResourceServer(oauth2
        -> oauth2.jwt(Customizer.withDefaults()))

                .exceptionHandling(handl -> handl.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("www.onet.pl");
                    logger.error("My access Denied handler: {}", accessDeniedException.getMessage());
                }).authenticationEntryPoint((request, response, authException) ->{
                    var redirectService= dc.getInstances("gateway").get(0);
                                    response.sendRedirect(String.valueOf(redirectService.getUri()
                                    ));
                }))
        ;

        return http.build();
    }
}
