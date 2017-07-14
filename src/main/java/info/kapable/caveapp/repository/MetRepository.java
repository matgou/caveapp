package info.kapable.caveapp.repository;

import info.kapable.caveapp.domain.Met;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Met entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetRepository extends JpaRepository<Met,Long> {
    
}
