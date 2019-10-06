package com.naranov.rest_client.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
//@EnableWebSecurity(debug = true)
////@EnableOAuth2Sso
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index").permitAll()
                .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login").usernameParameter("login").passwordParameter("password")
//                .and()
//                .logout().logoutUrl("/logout")
//                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")

                .and().logout()    //logout configuration
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll()
                .invalidateHttpSession(true)

                .and()
                .csrf().disable();
    }

}