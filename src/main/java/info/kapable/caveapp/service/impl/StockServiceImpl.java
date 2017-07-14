package info.kapable.caveapp.service.impl;

import info.kapable.caveapp.service.StockService;
import info.kapable.caveapp.domain.Stock;
import info.kapable.caveapp.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Stock.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService{

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Save a stock.
     *
     * @param stock the entity to save
     * @return the persisted entity
     */
    @Override
    public Stock save(Stock stock) {
        log.debug("Request to save Stock : {}", stock);
        return stockRepository.save(stock);
    }

    /**
     *  Get all the stocks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Stock> findAll() {
        log.debug("Request to get all Stocks");
        return stockRepository.findAll();
    }

    /**
     *  Get one stock by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Stock findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findOne(id);
    }

    /**
     *  Delete the  stock by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.delete(id);
    }
}
