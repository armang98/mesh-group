package com.mesh.group.test.config;

import com.mesh.group.test.service.impl.UserDetailServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailServiceImpl userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/**").authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
}