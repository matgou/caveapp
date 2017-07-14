package info.kapable.caveapp.service.impl;

import info.kapable.caveapp.service.VinService;
import info.kapable.caveapp.domain.Vin;
import info.kapable.caveapp.repository.VinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Vin.
 */
@Service
@Transactional
public class VinServiceImpl implements VinService{

    private final Logger log = LoggerFactory.getLogger(VinServiceImpl.class);

    private final VinRepository vinRepository;

    public VinServiceImpl(VinRepository vinRepository) {
        this.vinRepository = vinRepository;
    }

    /**
     * Save a vin.
     *
     * @param vin the entity to save
     * @return the persisted entity
     */
    @Override
    public Vin save(Vin vin) {
        log.debug("Request to save Vin : {}", vin);
        return vinRepository.save(vin);
    }

    /**
     *  Get all the vins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vin> findAll(Pageable pageable) {
        log.debug("Request to get all Vins");
        return vinRepository.findAll(pageable);
    }

    /**
     *  Get one vin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Vin findOne(Long id) {
        log.debug("Request to get Vin : {}", id);
        return vinRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  vin by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vin : {}", id);
        vinRepository.delete(id);
    }
}
