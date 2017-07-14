package info.kapable.caveapp.service;

import info.kapable.caveapp.domain.Met;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Met.
 */
public interface MetService {

    /**
     * Save a met.
     *
     * @param met the entity to save
     * @return the persisted entity
     */
    Met save(Met met);

    /**
     *  Get all the mets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Met> findAll(Pageable pageable);

    /**
     *  Get the "id" met.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Met findOne(Long id);

    /**
     *  Delete the "id" met.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
