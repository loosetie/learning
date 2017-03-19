package de.cdiag.ckl.javabasics.config;

import ac.simons.spring.boot.wro4j.processors.RemoveSourceMapsProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.isdc.wro.extensions.processor.css.Less4jProcessor;
import ro.isdc.wro.model.resource.processor.impl.css.LessCssImportPreProcessor;

/**
 * Created by Christian on 19.03.2017.
 */
@Configuration
public class Wro4jConfiguration {

	@Bean
	public LessCssImportPreProcessor wro4jLessCssImportPreProcessor() {
		return new LessCssImportPreProcessor();
	}

	@Bean
	public Less4jProcessor wro4jLess4jProcessor() {
		return new Less4jProcessor();
	}

	@Bean
	public RemoveSourceMapsProcessor wro4jRemoveSourceMapsProcessor() {
		return new RemoveSourceMapsProcessor();
	}
}
