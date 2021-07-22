package com.example.demo.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .csrf().disable()
                .authorizeRequests()
                // Require authentication for all requests under /caricaArticoli
                .antMatchers(HttpMethod.POST, "/create_articolo").authenticated()
                .antMatchers(HttpMethod.GET, "/addLike/*").authenticated()
                .antMatchers(HttpMethod.GET, "/addDislike/*").authenticated()
                // enable OAuth2/OIDC
                .and()
                .oauth2Login();

    }
}
