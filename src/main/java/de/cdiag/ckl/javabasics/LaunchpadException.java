package de.cdiag.ckl.javabasics;

/**
 * Created by Christian on 18.03.2017.
 */
public class LaunchpadException extends RuntimeException {

	public LaunchpadException( String message ) {
		super( message );
	}

	public LaunchpadException( String message, Throwable cause ) {
		super( message, cause );
	}
}
