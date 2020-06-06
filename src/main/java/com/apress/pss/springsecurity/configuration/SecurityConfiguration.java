package com.apress.pss.springsecurity.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends
        WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("user").password(passwordEncoder.encode("user123")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("admin123")).roles("USER",
                "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandle();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/welcome", "/login").permitAll()
//                .antMatchers("/authenticated/**").hasAnyRole("ADMIN", "USER")
//                .anyRequest().authenticated()
                .antMatchers("/**").hasRole("ADMIN")
                //.and().formLogin().loginPage("/login").permitAll()
                .and().httpBasic()
                    //.failureHandler(customAuthenticationFailureHandler()) //(new CustomAuthenticationFailureHandle())
                .and().logout().logoutSuccessUrl("/welcome").permitAll()
                .and().csrf().disable();
    }
}
