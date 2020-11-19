package com.example.ilmoitustaulu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.ilmoitustaulu.web.UserDetailServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl userDetailsService;	
    
    /* Määritetään sivut, joille käyttäjällä on pääsy kirjautumatta (listaus- ja kirjautumis-/rekisteröitymissivut)
     * Määritetään oletussivu jolle ohjataan kirjautumisen jälkeen
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests().antMatchers("/css/**").permitAll() // Enable css when logged out
        .and()
        .authorizeRequests().antMatchers("/signup", "/saveuser", "/messageboard", "/", "/myydään", "/vaihdetaan", "/ostetaan").permitAll()
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/messageboard")
          .permitAll()
          .and()
      .logout()
          .permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
}