package de.cdiag.ckl.javabasics.config;

import de.cdiag.ckl.javabasics.filter.CsrfHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Created by CKL on 17.03.2017.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure( HttpSecurity http )
		throws Exception {
		http
			.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers( "/index.html", "/home.html", "/login.html", "/wro4j/**", "/" ).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterAfter( new CsrfHeaderFilter(), CsrfFilter.class )
			.csrf().csrfTokenRepository( csrfTokenRepository() )
			.and()
			.logout()
		;
	}

	@Autowired
	public void globalUserDetails( AuthenticationManagerBuilder auth )
		throws Exception {
		auth.inMemoryAuthentication()
			.withUser( "user" ).password( "user" ).roles( "USER" )
			.and()
			.withUser( "test" ).password( "test" ).roles( "USER", "APP", "ACTUATOR" )
		;
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName( "X-XSRF-TOKEN" );
		return repository;
	}
}
