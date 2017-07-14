package info.kapable.caveapp.repository;

import info.kapable.caveapp.domain.TypeVin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeVin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeVinRepository extends JpaRepository<TypeVin,Long> {
    
}
