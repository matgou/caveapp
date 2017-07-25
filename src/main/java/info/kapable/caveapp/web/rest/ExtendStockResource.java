package info.kapable.caveapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import info.kapable.caveapp.domain.Stock;
import info.kapable.caveapp.domain.Vin;
import info.kapable.caveapp.service.StockService;
import info.kapable.caveapp.service.VinService;
import info.kapable.caveapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Stock.
 */
@RestController
@RequestMapping("/api")
public class ExtendStockResource {

    private final Logger log = LoggerFactory.getLogger(ExtendStockResource.class);

    private static final String ENTITY_NAME = "stock";

    private final StockService stockService;
    private final VinService vinService;

    public ExtendStockResource(StockService stockService, VinService vinService) {
        this.stockService = stockService;
        this.vinService = vinService;
    }

    @GetMapping("/stock-of/{id}")
    @Timed
    public List<Stock> getStock(@PathVariable Long id) throws NotFoundException {
        log.debug("REST request to get Stock : {}", id);
        Vin vin = vinService.findOne(id);
        if(vin == null) {
        	throw new NotFoundException("Vin with specified id not found");
        }
        return stockService.findByVin(vin);
        
    }
}
