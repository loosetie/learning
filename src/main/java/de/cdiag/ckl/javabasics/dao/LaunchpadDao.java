package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.jooq.tables.pojos.Launchpad;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 24.03.2017.
 */
@Service
@AllArgsConstructor
public class LaunchpadDao implements CrudDao<Launchpad> {

    private final DSLContext dsl;

    @Override
    public Launchpad store(Launchpad entity) {
        return null;
    }

    @Override
    public List<Launchpad> all() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Launchpad> get(Long id) {
        return Optional.empty();
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
