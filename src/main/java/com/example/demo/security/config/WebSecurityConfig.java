/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.security.config;

import com.example.demo.business.impl.AppUserServiceBOImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserServiceBOImpl appUserServiceBOImpl; // Reference Object to AppUserService
    private final BCryptPasswordEncoder bCryptPasswordEncoder;// Reference Object to bCryptPasswordEncoder


//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Give Permission to Enter Reset Password Form
        http.csrf().disable().authorizeRequests().antMatchers("/reset_password/reset/**").permitAll();
        // Give Permission to Register Page. (To the System)
        http.csrf().disable().authorizeRequests().antMatchers("/api/v*/registration/**").permitAll().anyRequest().authenticated().and().formLogin();
    }
//    public void authWithoutPassword(AppUser user){
//
//        List<Privilege> privileges = user.getAppUserRole().stream().map(Role::getPrivileges)
//                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
//        List<GrantedAuthority> authorities = privileges.stream()
//                .map(p -> new SimpleGrantedAuthority(p.getName()))
//                .collect(Collectors.toList());
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    @Override
    // configuring Spring Security to authenticate and authorize users
    protected void configure(AuthenticationManagerBuilder auth)  {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    // DaoAuthenticationProvider use the UserDetailsService to authenticate a username and password.
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserServiceBOImpl);
        return provider;
    }
}
