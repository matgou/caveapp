package info.kapable.caveapp.service;

import info.kapable.caveapp.domain.Millesime;
import java.util.List;

/**
 * Service Interface for managing Millesime.
 */
public interface MillesimeService {

    /**
     * Save a millesime.
     *
     * @param millesime the entity to save
     * @return the persisted entity
     */
    Millesime save(Millesime millesime);

    /**
     *  Get all the millesimes.
     *
     *  @return the list of entities
     */
    List<Millesime> findAll();

    /**
     *  Get the "id" millesime.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Millesime findOne(Long id);

    /**
     *  Delete the "id" millesime.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
