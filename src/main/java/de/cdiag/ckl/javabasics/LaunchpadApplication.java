package de.cdiag.ckl.javabasics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LaunchpadApplication {

	public static void main( String[] args ) {
		System.setProperty( "org.jooq.no-logo", "true" );
		SpringApplication.run( LaunchpadApplication.class, args );
	}

}
