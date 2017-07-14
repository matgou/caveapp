package info.kapable.caveapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import info.kapable.caveapp.domain.Met;
import info.kapable.caveapp.service.MetService;
import info.kapable.caveapp.web.rest.util.HeaderUtil;
import info.kapable.caveapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Met.
 */
@RestController
@RequestMapping("/api")
public class MetResource {

    private final Logger log = LoggerFactory.getLogger(MetResource.class);

    private static final String ENTITY_NAME = "met";

    private final MetService metService;

    public MetResource(MetService metService) {
        this.metService = metService;
    }

    /**
     * POST  /mets : Create a new met.
     *
     * @param met the met to create
     * @return the ResponseEntity with status 201 (Created) and with body the new met, or with status 400 (Bad Request) if the met has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mets")
    @Timed
    public ResponseEntity<Met> createMet(@Valid @RequestBody Met met) throws URISyntaxException {
        log.debug("REST request to save Met : {}", met);
        if (met.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new met cannot already have an ID")).body(null);
        }
        Met result = metService.save(met);
        return ResponseEntity.created(new URI("/api/mets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mets : Updates an existing met.
     *
     * @param met the met to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated met,
     * or with status 400 (Bad Request) if the met is not valid,
     * or with status 500 (Internal Server Error) if the met couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mets")
    @Timed
    public ResponseEntity<Met> updateMet(@Valid @RequestBody Met met) throws URISyntaxException {
        log.debug("REST request to update Met : {}", met);
        if (met.getId() == null) {
            return createMet(met);
        }
        Met result = metService.save(met);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, met.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mets : get all the mets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mets in body
     */
    @GetMapping("/mets")
    @Timed
    public ResponseEntity<List<Met>> getAllMets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Mets");
        Page<Met> page = metService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mets/:id : get the "id" met.
     *
     * @param id the id of the met to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the met, or with status 404 (Not Found)
     */
    @GetMapping("/mets/{id}")
    @Timed
    public ResponseEntity<Met> getMet(@PathVariable Long id) {
        log.debug("REST request to get Met : {}", id);
        Met met = metService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(met));
    }

    /**
     * DELETE  /mets/:id : delete the "id" met.
     *
     * @param id the id of the met to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMet(@PathVariable Long id) {
        log.debug("REST request to delete Met : {}", id);
        metService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
