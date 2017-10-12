package de.cdiag.ckl.javabasics.rest;

import de.cdiag.ckl.javabasics.dao.CrudDao;
import de.cdiag.ckl.javabasics.entities.HasId;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 03.04.2017.
 */
public abstract class AbstractRestController<T extends HasId> implements CrudController<T> {

    protected abstract CrudDao<T> dao();

    @Override
    public HttpEntity<List<T>> all() {
        List<T> templates = dao().all();
        return ResponseEntity.ok(templates);
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<T> create(@RequestBody T entity) {
        T template = dao().store(entity);
        return ResponseEntity.ok(template);
    }

    @Override
    public HttpEntity<T> get(@PathVariable("id") Long id) {
        Optional<T> template = dao().get(id);
        return template.isPresent()
                ? new ResponseEntity<>(template.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<T> update(@PathVariable("id") Long id, @RequestBody T entity) {
        entity.setId(id);
        T template = dao().store(entity);
        return ResponseEntity.ok(template);
    }

    @Override
    public HttpEntity<?> delete(@PathVariable("id") Long id) {
        int affectedRows = dao().delete(id);
        return affectedRows > 0
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
