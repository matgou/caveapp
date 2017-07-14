package info.kapable.caveapp.web.rest;

import info.kapable.caveapp.CaveappApp;

import info.kapable.caveapp.domain.Met;
import info.kapable.caveapp.repository.MetRepository;
import info.kapable.caveapp.service.MetService;
import info.kapable.caveapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MetResource REST controller.
 *
 * @see MetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaveappApp.class)
public class MetResourceIntTest {

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MetRepository metRepository;

    @Autowired
    private MetService metService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMetMockMvc;

    private Met met;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MetResource metResource = new MetResource(metService);
        this.restMetMockMvc = MockMvcBuilders.standaloneSetup(metResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Met createEntity(EntityManager em) {
        Met met = new Met()
            .categorie(DEFAULT_CATEGORIE)
            .description(DEFAULT_DESCRIPTION);
        return met;
    }

    @Before
    public void initTest() {
        met = createEntity(em);
    }

    @Test
    @Transactional
    public void createMet() throws Exception {
        int databaseSizeBeforeCreate = metRepository.findAll().size();

        // Create the Met
        restMetMockMvc.perform(post("/api/mets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(met)))
            .andExpect(status().isCreated());

        // Validate the Met in the database
        List<Met> metList = metRepository.findAll();
        assertThat(metList).hasSize(databaseSizeBeforeCreate + 1);
        Met testMet = metList.get(metList.size() - 1);
        assertThat(testMet.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testMet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metRepository.findAll().size();

        // Create the Met with an existing ID
        met.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetMockMvc.perform(post("/api/mets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(met)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Met> metList = metRepository.findAll();
        assertThat(metList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMets() throws Exception {
        // Initialize the database
        metRepository.saveAndFlush(met);

        // Get all the metList
        restMetMockMvc.perform(get("/api/mets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(met.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMet() throws Exception {
        // Initialize the database
        metRepository.saveAndFlush(met);

        // Get the met
        restMetMockMvc.perform(get("/api/mets/{id}", met.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(met.getId().intValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMet() throws Exception {
        // Get the met
        restMetMockMvc.perform(get("/api/mets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMet() throws Exception {
        // Initialize the database
        metService.save(met);

        int databaseSizeBeforeUpdate = metRepository.findAll().size();

        // Update the met
        Met updatedMet = metRepository.findOne(met.getId());
        updatedMet
            .categorie(UPDATED_CATEGORIE)
            .description(UPDATED_DESCRIPTION);

        restMetMockMvc.perform(put("/api/mets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMet)))
            .andExpect(status().isOk());

        // Validate the Met in the database
        List<Met> metList = metRepository.findAll();
        assertThat(metList).hasSize(databaseSizeBeforeUpdate);
        Met testMet = metList.get(metList.size() - 1);
        assertThat(testMet.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testMet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMet() throws Exception {
        int databaseSizeBeforeUpdate = metRepository.findAll().size();

        // Create the Met

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMetMockMvc.perform(put("/api/mets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(met)))
            .andExpect(status().isCreated());

        // Validate the Met in the database
        List<Met> metList = metRepository.findAll();
        assertThat(metList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMet() throws Exception {
        // Initialize the database
        metService.save(met);

        int databaseSizeBeforeDelete = metRepository.findAll().size();

        // Get the met
        restMetMockMvc.perform(delete("/api/mets/{id}", met.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Met> metList = metRepository.findAll();
        assertThat(metList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Met.class);
        Met met1 = new Met();
        met1.setId(1L);
        Met met2 = new Met();
        met2.setId(met1.getId());
        assertThat(met1).isEqualTo(met2);
        met2.setId(2L);
        assertThat(met1).isNotEqualTo(met2);
        met1.setId(null);
        assertThat(met1).isNotEqualTo(met2);
    }
}
