package com.ecommerce.config;

import com.ecommerce.security.JwtUtils;
import com.ecommerce.security.filter.CustomAuthenticationFilter;
import com.ecommerce.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(),jwtUtils);
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();
        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();
        // Public endpoints
        http.authorizeRequests()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers("/api/v1/brands").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user/").permitAll()
                .antMatchers("/api/v1/brand/{id:[\\\\d+]}/products").permitAll()
                .antMatchers("//api/v1/categories").permitAll()
                .antMatchers("/api/v1/category/{id:[\\\\d+]}/products").permitAll()
                .antMatchers("/api/v1/products").permitAll()
                .antMatchers("/api/v1/product/{id:[\\\\d+]}").permitAll()
                .antMatchers("/api/v1/tags").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
                .antMatchers("/api/v1/user/changePassword").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll();



        // Set permissions on endpoints
        http.authorizeRequests()
                .antMatchers("/api/v1/cart/**").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user/{id:[\\\\d+]}/orders").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/user/update").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/user/{id:[\\\\d+]}").hasAnyAuthority("ROLE_USER","ROLE_ADMIN","ROLE_SALE")
                .antMatchers(HttpMethod.GET, "/api/v1/orders").hasAnyAuthority("ROLE_SALE","ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/order/{id:[\\\\d+]}/items").hasAnyAuthority("ROLE_SALE","ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/order/{id:[\\\\d+]}/status/{status}").hasAnyAuthority("ROLE_SALE","ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/v1/**").hasAuthority("ROLE_ADMIN");
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
