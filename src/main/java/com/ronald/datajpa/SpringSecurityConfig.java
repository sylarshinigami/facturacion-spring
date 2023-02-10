package com.ronald.datajpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.ronald.datajpa.auth.handler.LoginSuccesHandler;
import com.ronald.datajpa.auth.service.JWTService;
import com.ronald.datajpa.models.service.JpaUserDetailService;

@EnableGlobalMethodSecurity(securedEnabled = true) // para trabajar con las anotaciones en los controller
@Configuration
public class SpringSecurityConfig {

	@Autowired
	DataSource dataSource;

	@Autowired
	private JpaUserDetailService userDetailService;

	@Bean
	public UserDetailsService userDetailsService(AuthenticationManagerBuilder build) throws Exception {
//para manejar usuario en memoria
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User
//				.withUsername("user")
//				.password(passwordEncoder().encode("user"))
//				.roles("USER")
//				.build());
//		manager.createUser(User
//				.withUsername("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN", "USER")
//				.build());
//
//		return manager;

		// para manejar los usuarios desde la base de datos.
//		build.jdbcAuthentication()
////				.dataSource(dataSource)
//				.passwordEncoder(passwordEncoder())
//				.usersByUsernameQuery("select username, password, enabled from users where username=?")
//				.authoritiesByUsernameQuery(
//						"select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");

		build.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());

		return build.getDefaultUserDetailsService();

	}

	@Autowired
	private LoginSuccesHandler succesHandler;
	
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    
    @Autowired
    private JWTService jwtService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar","/api/v1/**", "/locale").permitAll()
				.anyRequest().authenticated().and().formLogin().successHandler(succesHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");

		return http.build();
	}

	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
