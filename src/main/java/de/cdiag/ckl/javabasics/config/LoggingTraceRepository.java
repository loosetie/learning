package de.cdiag.ckl.javabasics.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.boot.actuate.trace.TraceRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by Christian on 18.03.2017.
 */
@RequiredArgsConstructor
@Slf4j
class LoggingTraceRepository implements TraceRepository {
	private final TraceRepository wrapped;

	@Override
	public List<Trace> findAll() {
		return wrapped.findAll();
	}

	@Override
	public void add( Map<String,Object> traceInfo ) {
		log.info( "Request: {}", traceInfo );
		wrapped.add( traceInfo );
	}
}
