package info.kapable.caveapp.service.impl;

import info.kapable.caveapp.service.MetService;
import info.kapable.caveapp.domain.Met;
import info.kapable.caveapp.repository.MetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Met.
 */
@Service
@Transactional
public class MetServiceImpl implements MetService{

    private final Logger log = LoggerFactory.getLogger(MetServiceImpl.class);

    private final MetRepository metRepository;

    public MetServiceImpl(MetRepository metRepository) {
        this.metRepository = metRepository;
    }

    /**
     * Save a met.
     *
     * @param met the entity to save
     * @return the persisted entity
     */
    @Override
    public Met save(Met met) {
        log.debug("Request to save Met : {}", met);
        return metRepository.save(met);
    }

    /**
     *  Get all the mets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Met> findAll(Pageable pageable) {
        log.debug("Request to get all Mets");
        return metRepository.findAll(pageable);
    }

    /**
     *  Get one met by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Met findOne(Long id) {
        log.debug("Request to get Met : {}", id);
        return metRepository.findOne(id);
    }

    /**
     *  Delete the  met by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Met : {}", id);
        metRepository.delete(id);
    }
}
