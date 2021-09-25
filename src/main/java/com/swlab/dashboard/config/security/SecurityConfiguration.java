package com.swlab.dashboard.config.security;

import com.swlab.dashboard.config.security.handler.CustomAuthenticationFailureHandler;
import com.swlab.dashboard.config.security.handler.CustomWebAccessDeniedHandler;
import com.swlab.dashboard.config.security.oauth.CustomOAuth2UserService;
import com.swlab.dashboard.service.SecurityUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomWebAccessDeniedHandler customWebAccessDeniedHandler;
    private final SecurityUserService securityUserService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/static/**");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(encoder.encode("password"))
                    .roles("USER")
                .and()
                    .withUser("admin")
                    .password(encoder.encode("admin"))
                    .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/login", "/", "/join", "/h2-console/**", "/denied", "/test", "/oauth2/**", "/api/**").permitAll()
                    .antMatchers("/home/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated()
                .and()
                    .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"))
                .and()
                    .authenticationProvider(authenticationProvider()).csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                    .redirectionEndpoint()
                    .baseUri("/login/oauth2/code/*")
                .and()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
