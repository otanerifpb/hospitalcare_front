package br.edu.ifpb.pdist.front.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.client.RestTemplate;

import br.edu.ifpb.pdist.front.service.CustomAuthenticationProvider;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/css/**", "/imagens/**").permitAll()
            .antMatchers("/auth").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin(form -> form 
                    .loginPage("/auth")  
                    .defaultSuccessUrl("/home", true) 
                    .permitAll())  
            .logout(logout -> logout.logoutUrl("/auth/logout")); 
    }

    // @Bean
    // @Override
    // protected UserDetailsService userDetailsService() {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //         .username("renato")
    //         .password("1234")
    //         .roles("ADM")
    //         .build();
        
    // }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }
    

}
