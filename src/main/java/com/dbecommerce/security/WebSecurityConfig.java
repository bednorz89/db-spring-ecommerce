package com.dbecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // authProvider.setPasswordEncoder(encoder()); //disable for tests
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v1/producers").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/producers/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/producers/{id}/products").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/producers").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/producers/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/producers").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/v1/products").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/products/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/products/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/products/{id}/carts").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/v1/products/{id}/carts").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/v1/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/v1/users/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/v1/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/users/carts").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/v1/users/carts").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/v1/users/orders").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/v1/users/orders").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/v1/orders").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/v1/orders/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/v1/orders/{id}/payments").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/v1/payments").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/v1/payments/{id]").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic().and().csrf().disable();
    }


}
