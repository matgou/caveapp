package info.kapable.caveapp.repository;

import info.kapable.caveapp.domain.Vin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Vin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VinRepository extends JpaRepository<Vin,Long> {

    @Query("select vin from Vin vin where vin.user.login = ?#{principal.username}")
    List<Vin> findByUserIsCurrentUser();
    
    @Query("select distinct vin from Vin vin left join fetch vin.mets")
    List<Vin> findAllWithEagerRelationships();

    @Query("select vin from Vin vin left join fetch vin.mets where vin.id =:id")
    Vin findOneWithEagerRelationships(@Param("id") Long id);
    
}
