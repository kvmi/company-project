package com.project.klewandowski.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("admin")).authorities("ROLE_USER", "ROLE_ADMIN")
//                .and()
//                .withUser("user").password(passwordEncoder.encode("user")).authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signup").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/addUser").hasAnyRole("ADMIN")
//                .antMatchers("/**").authenticated()
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().defaultSuccessUrl("/user/main").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");


    }
}
