package info.kapable.caveapp.service.impl;

import info.kapable.caveapp.service.MillesimeService;
import info.kapable.caveapp.domain.Millesime;
import info.kapable.caveapp.repository.MillesimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Millesime.
 */
@Service
@Transactional
public class MillesimeServiceImpl implements MillesimeService{

    private final Logger log = LoggerFactory.getLogger(MillesimeServiceImpl.class);

    private final MillesimeRepository millesimeRepository;

    public MillesimeServiceImpl(MillesimeRepository millesimeRepository) {
        this.millesimeRepository = millesimeRepository;
    }

    /**
     * Save a millesime.
     *
     * @param millesime the entity to save
     * @return the persisted entity
     */
    @Override
    public Millesime save(Millesime millesime) {
        log.debug("Request to save Millesime : {}", millesime);
        return millesimeRepository.save(millesime);
    }

    /**
     *  Get all the millesimes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Millesime> findAll() {
        log.debug("Request to get all Millesimes");
        return millesimeRepository.findAll();
    }

    /**
     *  Get one millesime by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Millesime findOne(Long id) {
        log.debug("Request to get Millesime : {}", id);
        return millesimeRepository.findOne(id);
    }

    /**
     *  Delete the  millesime by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Millesime : {}", id);
        millesimeRepository.delete(id);
    }
}
