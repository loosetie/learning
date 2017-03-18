package de.cdiag.ckl.javabasics.config;

import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Christian on 18.03.2017.
 */
@Configuration
public class ActuatorConfiguration {

	@Bean
	public TraceRepository traceRepository() {
		return new LoggingTraceRepository( new InMemoryTraceRepository() );
	}

}
