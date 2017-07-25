package info.kapable.caveapp.repository;

import info.kapable.caveapp.domain.Stock;
import info.kapable.caveapp.domain.Vin;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

	@Query("SELECT s FROM Stock s WHERE s.vin = ?1a")
	List<Stock> findByVin(Vin vin);
    
}
