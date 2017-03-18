package de.cdiag.ckl.javabasics.rest;

import de.cdiag.ckl.javabasics.dao.AppDao;
import de.cdiag.ckl.javabasics.jooq.tables.pojos.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(apps);
    }

    @Override
    public HttpEntity<App> create(@RequestBody App entity) {
        return put(entity);
    }

    @Override
    public HttpEntity<App> get(@PathVariable("id") Long id) {
        App app = appDao.get(id);
        return ResponseEntity.ok(app);
    }

    @Override
    public HttpEntity<App> put(@RequestBody App entity) {
        App app = appDao.store(entity);
        return ResponseEntity.ok(app);
    }

    @Override
    public HttpEntity<App> delete(@PathVariable("id") Long id) {
        App app = appDao.delete(id);
        return ResponseEntity.ok(app);
    }

}
