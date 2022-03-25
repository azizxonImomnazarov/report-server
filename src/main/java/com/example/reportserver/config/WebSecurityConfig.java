package com.example.reportserver.config;

import com.example.reportserver.security.handler.CustomSuccessHandler;
import com.example.reportserver.security.filter.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    private final CustomFilter customFilter;
    private final CustomSuccessHandler customSuccessHandler;

    public WebSecurityConfig(CustomFilter customFilter, CustomSuccessHandler customSuccessHandler) {
        this.customFilter = customFilter;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().disable().csrf().disable();

        http.formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
                .successHandler(customSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/api/common/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(customFilter, BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
