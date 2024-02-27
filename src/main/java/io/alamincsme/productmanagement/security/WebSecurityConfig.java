package io.alamincsme.productmanagement.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(requests -> requests
//                        .requestMatchers("/delete/**").hasAuthority("ADMIN")
//                        .requestMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
//                        .requestMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
//                        .requestMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
//                        .anyRequest().authenticated())
//                .formLogin(login -> login.permitAll())
//                .logout(logout -> logout.permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
//                .exceptionHandling(handling -> handling.accessDeniedPage("/403"));

        http
                .authorizeHttpRequests((req) -> req
                                .requestMatchers("/").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers("/new").hasAnyAuthority("ADMIN")
                                .requestMatchers("/edit/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/delete/**").hasAnyAuthority("ADMIN")
                                .anyRequest().authenticated()

                        )
                .formLogin(login -> login.permitAll())
                .logout(logout -> logout.permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(handling -> handling.accessDeniedPage("/403"));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
