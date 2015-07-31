package com.gtan.singerdream;

import com.gtan.singerdream.filter.StatelessAuthenticationFilter;
import com.gtan.singerdream.filter.StatelessManagerLoginFilter;
import com.gtan.singerdream.service.ManagerService;
import com.gtan.singerdream.service.ManagerTokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by user on 15-7-30.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class StatelessSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerTokenAuthenticationService managerTokenAuthenticationService;

    public StatelessSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                //allow anonymous resource requests
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().and()
                .authorizeRequests()
               // .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/page/**").permitAll()
                .antMatchers("/bower_components/**").
                permitAll().antMatchers("/login.html").
                permitAll().antMatchers("/").hasRole("ADMIN")
               // .antMatchers("/*").permitAll()

                //allow anonymous POSTs to login
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()

                .antMatchers(HttpMethod.POST, "/api/backend/login").permitAll()
                .anyRequest().authenticated().
                antMatchers("/api/backend/**").hasRole("ADMIN").and()
                .formLogin().loginPage("/login.html").permitAll().and().
                addFilterBefore(new StatelessManagerLoginFilter("/api/backend/login", managerTokenAuthenticationService,
                                managerService),
                        UsernamePasswordAuthenticationFilter.class).
                addFilterBefore(new StatelessAuthenticationFilter(managerTokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }
}
