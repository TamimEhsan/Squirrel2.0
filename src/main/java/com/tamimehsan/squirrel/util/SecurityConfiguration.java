package com.tamimehsan.squirrel.util;

import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,'true' as enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, authority from users where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/admin","/admin/**").hasRole("ADMIN")
                .antMatchers("/signup","signup/").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/**").hasAnyRole("CUSTOMER","ADMIN")
                .and().formLogin().loginPage("/login").permitAll()
                .successHandler((request, response, authentication) -> {
                    // run custom logics upon successful login

                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();
                    User user = userRepository.findByUsername(username);
                    String authority = user.getAuthority();
                    String redirectURL = request.getContextPath();

                    System.out.println(authority);
                    if (authority.equals("ROLE_CUSTOMER")) {
                        redirectURL = "/";
                    }
//                    else if (authority.equals("ROLE_SHOP")) {
//                        redirectURL = "shop/home";
//                    }
                    else if(authority.equals("ROLE_ADMIN")){
                        redirectURL = "admin/book";
                    }

                    response.sendRedirect(redirectURL);
                })
                .and().exceptionHandling().accessDeniedPage("/access-denied");


    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

}
