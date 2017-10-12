package de.cdiag.ckl.javabasics.rest;

import de.cdiag.ckl.javabasics.dao.CrudDao;
import de.cdiag.ckl.javabasics.dao.TemplateDao;
import de.cdiag.ckl.javabasics.entities.TemplateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class TemplateRestController extends AbstractRestController<TemplateEntity> {

	private final TemplateDao dao;

	@Override
	protected CrudDao<TemplateEntity> dao() {
		return dao;
	}
}
