package info.kapable.caveapp.service;

import info.kapable.caveapp.domain.TypeVin;
import java.util.List;

/**
 * Service Interface for managing TypeVin.
 */
public interface TypeVinService {

    /**
     * Save a typeVin.
     *
     * @param typeVin the entity to save
     * @return the persisted entity
     */
    TypeVin save(TypeVin typeVin);

    /**
     *  Get all the typeVins.
     *
     *  @return the list of entities
     */
    List<TypeVin> findAll();

    /**
     *  Get the "id" typeVin.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeVin findOne(Long id);

    /**
     *  Delete the "id" typeVin.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
