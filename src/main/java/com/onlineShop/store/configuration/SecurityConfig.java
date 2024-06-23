package com.onlineShop.store.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.onlineShop.store.services.CustomOAuth2UserService;
import com.onlineShop.store.services.LoginSuccessHandler;
import com.onlineShop.store.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final CustomOAuth2UserService oauth2UserService;
  private final LoginSuccessHandler loginSuccessHandler;

  @Bean
  public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userService.userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder);
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(HttpMethod.POST, "/signin", "/registration", "/home/**","/orders/add").permitAll()
	            .requestMatchers(HttpMethod.GET, "/products/id/{id}", "/products/{name}", "/category/getAll", 
	                             "/products/getRatings/{id}", "products/brands/unique/{limit}", "/confirm/**", 
	                             "/products/admin/id/{id}","/comments/**","/home/**").permitAll()
	         
	            .requestMatchers(HttpMethod.POST, "/products/setRatings","/comments/add/**").authenticated()
	            .requestMatchers(HttpMethod.GET, "/users/accounts/{id}", "/users/{id}/orders").authenticated()
	            .requestMatchers(HttpMethod.POST, "/users/accounts/{id}").authenticated()
	            .requestMatchers(HttpMethod.GET, "/orders/**").hasAuthority("ROLE_MODERATOR")
	            .requestMatchers(HttpMethod.POST, "/orders/**","/comment/delete/**").hasAuthority("ROLE_MODERATOR")
	            .requestMatchers(HttpMethod.GET, "/admin/**", "/users/**", "/products/**", "/category/**").hasAuthority("ROLE_ADMIN")
	            .requestMatchers(HttpMethod.POST, "/admin/**", "/users/**", "/products/**", "/category/**").hasAuthority("ROLE_ADMIN")
	            .anyRequest().authenticated()
	        )
	        .authenticationProvider(authenticationProvider())
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	        
	        .cors()
	        .and()
	        .headers()
	        .xssProtection()
	        .and()
	        .contentSecurityPolicy("script-src 'self'");
	    
	    http.oauth2Login()
 	    .successHandler(loginSuccessHandler)
        .failureUrl("http://85.217.171.56:8081/login")
        .userInfoEndpoint()
        .userService(oauth2UserService);
	    
	    return http.build();
	}

  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081","http://85.217.171.56:8081")); 
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","OPTIONS")); 
	    configuration.setAllowCredentials(true); 
	    configuration.setAllowedHeaders(Arrays.asList("*")); 
	    configuration.addExposedHeader("Authorization"); 
	    configuration.addExposedHeader("X-Xsrf-Token");//
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
  }
  
  @Bean
  public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping("/**")
                  .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                  .allowedHeaders("*")
                  .allowedOrigins("http://localhost:8081","http://85.217.171.56:8081")
                  .allowCredentials(true)
                  .exposedHeaders("Authorization","X-Xsrf-Token");
          }
      };
  }
  

}
