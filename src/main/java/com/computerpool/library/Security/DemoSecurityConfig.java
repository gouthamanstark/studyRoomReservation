package com.computerpool.library.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class DemoSecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT s.student_id,r.password,r.enabled from students s join student_details r on s.details_id=r.details_id where  s.student_id=?");
        
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT s.student_id,d.roles from students s join student_details d on s.details_id=d.details_id where s.student_id=?");
        
        return jdbcUserDetailsManager;
    }
 


 @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


       http
        
        .authorizeHttpRequests(configurer->
        configurer
        .requestMatchers("/passwordResetForm","student/login/nfcTag","/passwordResetConfirmation","/passwordReset","/js/**","/css/**","/img/**","/confirmation").permitAll()
        .requestMatchers("/student/**").hasAnyAuthority("Student","Admin")
        
        .requestMatchers("/admin/**").hasAuthority("Admin")
        .anyRequest().authenticated()      );
        
        http.logout(logout->
        logout.invalidateHttpSession(true)
        .logoutSuccessUrl("/login")
);

    

        http.formLogin(customizer-> customizer.loginPage("/login").permitAll().successHandler(authenticationSuccessHandler));

        return http.build();
        
    }
}
 

