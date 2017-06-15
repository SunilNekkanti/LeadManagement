package com.pfchoice.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pfchoice.springboot.security.CustomAuthenticationSuccessHandler;
import com.pfchoice.springboot.security.RestAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment env;

	@Autowired
	UserDetailsService authenticationService;

//	@Autowired
//	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	 
	    
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**", "/");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers()
		.hasAnyAuthority("ROLE_AGENT", "ROLE_ADMIN", "ROLE_SELECTOR").anyRequest().authenticated().and().formLogin().loginPage("/").usernameParameter("username").passwordParameter("password")
		.loginProcessingUrl("/loginform.do").successHandler(new CustomAuthenticationSuccessHandler())
		.failureUrl("/login?error").and()

		.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true)
		.deleteCookies("JSESSIONID").permitAll().and()

		.exceptionHandling().accessDeniedPage("/403").and()

		.csrf().disable()

		.sessionManagement().maximumSessions(1);
	}

	/**
	 * use below two lines when encoder is enabled in password
	 * ShaPasswordEncoder encoder = new ShaPasswordEncoder();
	 * auth.userDetailsService(authenticationService).passwordEncoder(encoder);
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.userDetailsService(authenticationService).passwordEncoder(encoder);
		auth.userDetailsService(authenticationService);
	}
}
