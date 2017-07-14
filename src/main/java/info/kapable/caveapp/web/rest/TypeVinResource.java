package info.kapable.caveapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import info.kapable.caveapp.domain.TypeVin;
import info.kapable.caveapp.service.TypeVinService;
import info.kapable.caveapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeVin.
 */
@RestController
@RequestMapping("/api")
public class TypeVinResource {

    private final Logger log = LoggerFactory.getLogger(TypeVinResource.class);

    private static final String ENTITY_NAME = "typeVin";

    private final TypeVinService typeVinService;

    public TypeVinResource(TypeVinService typeVinService) {
        this.typeVinService = typeVinService;
    }

    /**
     * POST  /type-vins : Create a new typeVin.
     *
     * @param typeVin the typeVin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeVin, or with status 400 (Bad Request) if the typeVin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-vins")
    @Timed
    public ResponseEntity<TypeVin> createTypeVin(@Valid @RequestBody TypeVin typeVin) throws URISyntaxException {
        log.debug("REST request to save TypeVin : {}", typeVin);
        if (typeVin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new typeVin cannot already have an ID")).body(null);
        }
        TypeVin result = typeVinService.save(typeVin);
        return ResponseEntity.created(new URI("/api/type-vins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-vins : Updates an existing typeVin.
     *
     * @param typeVin the typeVin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeVin,
     * or with status 400 (Bad Request) if the typeVin is not valid,
     * or with status 500 (Internal Server Error) if the typeVin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-vins")
    @Timed
    public ResponseEntity<TypeVin> updateTypeVin(@Valid @RequestBody TypeVin typeVin) throws URISyntaxException {
        log.debug("REST request to update TypeVin : {}", typeVin);
        if (typeVin.getId() == null) {
            return createTypeVin(typeVin);
        }
        TypeVin result = typeVinService.save(typeVin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeVin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-vins : get all the typeVins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeVins in body
     */
    @GetMapping("/type-vins")
    @Timed
    public List<TypeVin> getAllTypeVins() {
        log.debug("REST request to get all TypeVins");
        return typeVinService.findAll();
    }

    /**
     * GET  /type-vins/:id : get the "id" typeVin.
     *
     * @param id the id of the typeVin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeVin, or with status 404 (Not Found)
     */
    @GetMapping("/type-vins/{id}")
    @Timed
    public ResponseEntity<TypeVin> getTypeVin(@PathVariable Long id) {
        log.debug("REST request to get TypeVin : {}", id);
        TypeVin typeVin = typeVinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeVin));
    }

    /**
     * DELETE  /type-vins/:id : delete the "id" typeVin.
     *
     * @param id the id of the typeVin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-vins/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeVin(@PathVariable Long id) {
        log.debug("REST request to delete TypeVin : {}", id);
        typeVinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
