package info.kapable.caveapp.repository;

import info.kapable.caveapp.domain.Millesime;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Millesime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MillesimeRepository extends JpaRepository<Millesime,Long> {
    
}
