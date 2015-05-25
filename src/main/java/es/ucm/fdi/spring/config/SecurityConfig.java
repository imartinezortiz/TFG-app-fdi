package es.ucm.fdi.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.ucm.fdi.users.business.boundary.UsersManager;
import es.ucm.fdi.users.spring.security.UsersDetailsManager;
import es.ucm.fdi.users.util.PBKDF2PasswordEncoder;

@Configuration
//@ComponentScan({ "es.ucm.fdi.users.spring.security" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	Environment env;
	
	@Autowired
	UsersManager usersManager;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(customUserDetailsService()).passwordEncoder(
				passwordEncoder());
	}

	@Bean   
	public UserDetailsService customUserDetailsService() {
		return new UsersDetailsManager(usersManager);
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PBKDF2PasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/static/**", "/webjars/**")
				.permitAll();
		http.authorizeRequests().antMatchers("/login*").anonymous();
		http.authorizeRequests().antMatchers("/users/**").hasRole("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin().loginProcessingUrl("/login").loginPage("/loginPage")
				.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/avisos/")
				.failureUrl("/loginPage?auth=error");
		http.rememberMe().key(env.getRequiredProperty("rememberMe.secret")).tokenValiditySeconds(7200);
	}

}
