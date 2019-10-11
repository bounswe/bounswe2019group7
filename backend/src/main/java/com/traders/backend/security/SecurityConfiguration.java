package com.traders.backend.security;


import com.traders.backend.security.filters.JwtBasicAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Configuration
    @Order(1)
    public static class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/basic")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilter(new JwtBasicAuthenticationFilter(authenticationManager()))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable();
        }

    }

    @Configuration
    @Order(2)
    public static class TraderSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/trader")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilter(new JwtBasicAuthenticationFilter(authenticationManager()))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable();
        }

    }


}
