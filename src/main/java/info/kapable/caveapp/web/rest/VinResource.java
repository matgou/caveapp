package info.kapable.caveapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import info.kapable.caveapp.domain.Vin;
import info.kapable.caveapp.service.VinService;
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
 * REST controller for managing Vin.
 */
@RestController
@RequestMapping("/api")
public class VinResource {

    private final Logger log = LoggerFactory.getLogger(VinResource.class);

    private static final String ENTITY_NAME = "vin";

    private final VinService vinService;

    public VinResource(VinService vinService) {
        this.vinService = vinService;
    }

    /**
     * POST  /vins : Create a new vin.
     *
     * @param vin the vin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vin, or with status 400 (Bad Request) if the vin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vins")
    @Timed
    public ResponseEntity<Vin> createVin(@Valid @RequestBody Vin vin) throws URISyntaxException {
        log.debug("REST request to save Vin : {}", vin);
        if (vin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vin cannot already have an ID")).body(null);
        }
        Vin result = vinService.save(vin);
        return ResponseEntity.created(new URI("/api/vins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vins : Updates an existing vin.
     *
     * @param vin the vin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vin,
     * or with status 400 (Bad Request) if the vin is not valid,
     * or with status 500 (Internal Server Error) if the vin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vins")
    @Timed
    public ResponseEntity<Vin> updateVin(@Valid @RequestBody Vin vin) throws URISyntaxException {
        log.debug("REST request to update Vin : {}", vin);
        if (vin.getId() == null) {
            return createVin(vin);
        }
        Vin result = vinService.save(vin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vins : get all the vins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vins in body
     */
    @GetMapping("/vins")
    @Timed
    public ResponseEntity<List<Vin>> getAllVins(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Vins");
        Page<Vin> page = vinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vins/:id : get the "id" vin.
     *
     * @param id the id of the vin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vin, or with status 404 (Not Found)
     */
    @GetMapping("/vins/{id}")
    @Timed
    public ResponseEntity<Vin> getVin(@PathVariable Long id) {
        log.debug("REST request to get Vin : {}", id);
        Vin vin = vinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vin));
    }

    /**
     * DELETE  /vins/:id : delete the "id" vin.
     *
     * @param id the id of the vin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vins/{id}")
    @Timed
    public ResponseEntity<Void> deleteVin(@PathVariable Long id) {
        log.debug("REST request to delete Vin : {}", id);
        vinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
