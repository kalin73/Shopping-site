package com.example.shopping.config;

import com.example.shopping.repository.UserRepository;
import com.example.shopping.service.AuthenticatedUserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain secFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/auth/**", "/", "/add/**", "/api/**", "/products/**", "/reviews", "/product/**", "/categories", "/aboutUs")
                        .permitAll()
                        .requestMatchers("/deleteCart", "/delete/**", "/order/**", "/profile", "/billingAddress", "/creditCard/**")
                        .authenticated())
                .formLogin(
                        login -> login.loginPage("/auth/login").usernameParameter("email").passwordParameter("password")
                                .defaultSuccessUrl("/", true).failureForwardUrl("/auth/login-error"))
                .logout(out -> out.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true))
                .rememberMe(me -> me.key("someUniqueKey").tokenValiditySeconds(604800))
                .cors(CorsConfigurer::disable)
                .csrf(CsrfConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetails(UserRepository userRepository) {
        return new AuthenticatedUserService(userRepository);
    }
}
