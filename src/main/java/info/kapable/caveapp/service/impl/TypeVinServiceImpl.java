package info.kapable.caveapp.service.impl;

import info.kapable.caveapp.service.TypeVinService;
import info.kapable.caveapp.domain.TypeVin;
import info.kapable.caveapp.repository.TypeVinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TypeVin.
 */
@Service
@Transactional
public class TypeVinServiceImpl implements TypeVinService{

    private final Logger log = LoggerFactory.getLogger(TypeVinServiceImpl.class);

    private final TypeVinRepository typeVinRepository;

    public TypeVinServiceImpl(TypeVinRepository typeVinRepository) {
        this.typeVinRepository = typeVinRepository;
    }

    /**
     * Save a typeVin.
     *
     * @param typeVin the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeVin save(TypeVin typeVin) {
        log.debug("Request to save TypeVin : {}", typeVin);
        return typeVinRepository.save(typeVin);
    }

    /**
     *  Get all the typeVins.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeVin> findAll() {
        log.debug("Request to get all TypeVins");
        return typeVinRepository.findAll();
    }

    /**
     *  Get one typeVin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TypeVin findOne(Long id) {
        log.debug("Request to get TypeVin : {}", id);
        return typeVinRepository.findOne(id);
    }

    /**
     *  Delete the  typeVin by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeVin : {}", id);
        typeVinRepository.delete(id);
    }
}
