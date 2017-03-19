package de.cdiag.ckl.javabasics.rest;

import de.cdiag.ckl.javabasics.dao.CrudDao;
import de.cdiag.ckl.javabasics.entities.TemplateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/rest/v1/tpl")
@RequiredArgsConstructor
public class TemplateRestController implements CrudController<TemplateEntity> {

	private final CrudDao<TemplateEntity> dao;

	public HttpEntity<List<TemplateEntity>> all() {
		List<TemplateEntity> templates = dao.all();
		return ResponseEntity.ok( templates );
	}

	@Override
	public HttpEntity<TemplateEntity> create( @RequestBody TemplateEntity entity ) {
		TemplateEntity template = dao.store( entity );
		return ResponseEntity.ok( template );
	}

	@Override
	public HttpEntity<TemplateEntity> get( @PathVariable("id") Long id ) {
		Optional<TemplateEntity> template = dao.get( id );
		return template.isPresent()
						 ? new ResponseEntity<>( template.get(), HttpStatus.OK )
						 : new ResponseEntity<>( HttpStatus.NOT_FOUND );
	}

	@Override
	public HttpEntity<TemplateEntity> update( @PathVariable("id") Long id, @RequestBody TemplateEntity entity ) {
		entity.setId( id );
		TemplateEntity template = dao.store( entity );
		return ResponseEntity.ok( template );
	}

	@Override
	@Transactional
	public HttpEntity<?> delete( @PathVariable("id") Long id ) {
		int affectedRows = dao.delete( id );
		return affectedRows > 0
						 ? new ResponseEntity<>( HttpStatus.OK )
						 : new ResponseEntity<>( HttpStatus.NOT_FOUND );
	}

}
