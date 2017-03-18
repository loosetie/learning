package de.cdiag.ckl.javabasics.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by CKL on 17.03.2017.
 */
@RestController
public class BasicRestController {

	@RequestMapping("/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<>();
		model.put( "id", UUID.randomUUID().toString() );
		model.put( "content", "Hello World from your beloved server!s" );
		return model;
	}

	@RequestMapping("/user")
	public Principal user( Principal user ) {
		return user;
	}
}
