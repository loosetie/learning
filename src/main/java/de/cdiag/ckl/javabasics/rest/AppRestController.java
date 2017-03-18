package de.cdiag.ckl.javabasics.rest;

import de.cdiag.ckl.javabasics.dao.AppDao;
import de.cdiag.ckl.javabasics.jooq.tables.pojos.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 17.03.2017.
 */
@RestController
@RequestMapping("/rest/v1/app")
@RequiredArgsConstructor
public class AppRestController implements CrudController<App> {

	private final AppDao appDao;

	public HttpEntity<List<App>> all() {
		List<App> apps = appDao.all();
		return ResponseEntity.ok( apps );
	}

	@Override
	public HttpEntity<App> create( @RequestBody App entity ) {
		return update( entity );
	}

	@Override
	public HttpEntity<App> get( @PathVariable("id") Long id ) {
		Optional<App> app = appDao.get( id );
		return app.isPresent()
						 ? new ResponseEntity<>( app.get(), HttpStatus.OK )
						 : new ResponseEntity<>( HttpStatus.NOT_FOUND );
	}

	@Override
	public HttpEntity<App> update( @RequestBody App entity ) {
		App app = appDao.store( entity );
		return ResponseEntity.ok( app );
	}

	@Override
	public HttpEntity<?> delete( @PathVariable("id") Long id ) {
		int affectedRows = appDao.delete( id );
		return affectedRows > 0
						 ? new ResponseEntity<>( HttpStatus.OK )
						 : new ResponseEntity<>( HttpStatus.NOT_FOUND );
	}

}
