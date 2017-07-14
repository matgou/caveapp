package info.kapable.caveapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import info.kapable.caveapp.domain.Millesime;
import info.kapable.caveapp.service.MillesimeService;
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
 * REST controller for managing Millesime.
 */
@RestController
@RequestMapping("/api")
public class MillesimeResource {

    private final Logger log = LoggerFactory.getLogger(MillesimeResource.class);

    private static final String ENTITY_NAME = "millesime";

    private final MillesimeService millesimeService;

    public MillesimeResource(MillesimeService millesimeService) {
        this.millesimeService = millesimeService;
    }

    /**
     * POST  /millesimes : Create a new millesime.
     *
     * @param millesime the millesime to create
     * @return the ResponseEntity with status 201 (Created) and with body the new millesime, or with status 400 (Bad Request) if the millesime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/millesimes")
    @Timed
    public ResponseEntity<Millesime> createMillesime(@Valid @RequestBody Millesime millesime) throws URISyntaxException {
        log.debug("REST request to save Millesime : {}", millesime);
        if (millesime.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new millesime cannot already have an ID")).body(null);
        }
        Millesime result = millesimeService.save(millesime);
        return ResponseEntity.created(new URI("/api/millesimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /millesimes : Updates an existing millesime.
     *
     * @param millesime the millesime to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated millesime,
     * or with status 400 (Bad Request) if the millesime is not valid,
     * or with status 500 (Internal Server Error) if the millesime couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/millesimes")
    @Timed
    public ResponseEntity<Millesime> updateMillesime(@Valid @RequestBody Millesime millesime) throws URISyntaxException {
        log.debug("REST request to update Millesime : {}", millesime);
        if (millesime.getId() == null) {
            return createMillesime(millesime);
        }
        Millesime result = millesimeService.save(millesime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, millesime.getId().toString()))
            .body(result);
    }

    /**
     * GET  /millesimes : get all the millesimes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of millesimes in body
     */
    @GetMapping("/millesimes")
    @Timed
    public List<Millesime> getAllMillesimes() {
        log.debug("REST request to get all Millesimes");
        return millesimeService.findAll();
    }

    /**
     * GET  /millesimes/:id : get the "id" millesime.
     *
     * @param id the id of the millesime to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the millesime, or with status 404 (Not Found)
     */
    @GetMapping("/millesimes/{id}")
    @Timed
    public ResponseEntity<Millesime> getMillesime(@PathVariable Long id) {
        log.debug("REST request to get Millesime : {}", id);
        Millesime millesime = millesimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(millesime));
    }

    /**
     * DELETE  /millesimes/:id : delete the "id" millesime.
     *
     * @param id the id of the millesime to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/millesimes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMillesime(@PathVariable Long id) {
        log.debug("REST request to delete Millesime : {}", id);
        millesimeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
