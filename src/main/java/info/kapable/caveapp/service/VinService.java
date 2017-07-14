package info.kapable.caveapp.service;

import info.kapable.caveapp.domain.Vin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Vin.
 */
public interface VinService {

    /**
     * Save a vin.
     *
     * @param vin the entity to save
     * @return the persisted entity
     */
    Vin save(Vin vin);

    /**
     *  Get all the vins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Vin> findAll(Pageable pageable);

    /**
     *  Get the "id" vin.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Vin findOne(Long id);

    /**
     *  Delete the "id" vin.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
