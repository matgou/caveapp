package info.kapable.caveapp.service;

import info.kapable.caveapp.domain.Stock;
import java.util.List;

/**
 * Service Interface for managing Stock.
 */
public interface StockService {

    /**
     * Save a stock.
     *
     * @param stock the entity to save
     * @return the persisted entity
     */
    Stock save(Stock stock);

    /**
     *  Get all the stocks.
     *
     *  @return the list of entities
     */
    List<Stock> findAll();

    /**
     *  Get the "id" stock.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Stock findOne(Long id);

    /**
     *  Delete the "id" stock.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
