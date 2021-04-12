package com.swlab.dashboard.config.security;

import com.swlab.dashboard.config.security.handler.AuthFailureHandler;
import com.swlab.dashboard.config.security.handler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AuthFailureHandler authFailureHandler;


    public void configure(WebSecurity security) throws Exception {
        security.ignoring().antMatchers("/static/**");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
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
                    .authorizeRequests()
                    .antMatchers("/login", "/", "/join").permitAll()
                    .antMatchers("/home/**").access("hasRole('USER')")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/home", true)
                    .failureUrl("/login?error=true")
                    .failureHandler(authenticationFailureHandler())
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(logoutSuccessHandler());
    }
}
