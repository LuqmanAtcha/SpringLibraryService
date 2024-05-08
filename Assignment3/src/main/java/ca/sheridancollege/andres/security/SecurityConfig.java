package ca.sheridancollege.andres.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception
	{
		MvcRequestMatcher.Builder mvc=new MvcRequestMatcher.Builder(introspector);
		
		return http.authorizeHttpRequests(authorize -> authorize
				
				.requestMatchers(mvc.pattern("/admin/**")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/addBook/**")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/user/**")).hasRole("USER")
				.requestMatchers(mvc.pattern("/**")).permitAll()
				.requestMatchers(mvc.pattern("/js/**")).permitAll()
				.requestMatchers(mvc.pattern("/bookReviews/**")).permitAll()
				.requestMatchers(mvc.pattern("/addReview/**")).permitAll()
				.requestMatchers(mvc.pattern("permissionDenied")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/register")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/register")).permitAll()
				//.requestMatchers(mvc.pattern("/**")).denyAll()
				
				)
				.csrf(csrf -> csrf
				.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
				.formLogin(form -> form.loginPage("/login").permitAll())
				.logout(logout -> logout.permitAll())
				.exceptionHandling(exception -> exception.accessDeniedPage("/permissionDenied"))
				.build();		
	}
}
