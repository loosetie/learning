package de.cdiag.ckl.javabasics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class JavaBasicsSpringApplication {

	public static void main( String[] args ) {
		SpringApplication.run( JavaBasicsSpringApplication.class, args );
	}

}
