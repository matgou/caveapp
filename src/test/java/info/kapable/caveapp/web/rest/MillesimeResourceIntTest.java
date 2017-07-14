package info.kapable.caveapp.web.rest;

import info.kapable.caveapp.CaveappApp;

import info.kapable.caveapp.domain.Millesime;
import info.kapable.caveapp.repository.MillesimeRepository;
import info.kapable.caveapp.service.MillesimeService;
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
 * Test class for the MillesimeResource REST controller.
 *
 * @see MillesimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaveappApp.class)
public class MillesimeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "1561";
    private static final String UPDATED_LIBELLE = "1665";

    @Autowired
    private MillesimeRepository millesimeRepository;

    @Autowired
    private MillesimeService millesimeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMillesimeMockMvc;

    private Millesime millesime;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MillesimeResource millesimeResource = new MillesimeResource(millesimeService);
        this.restMillesimeMockMvc = MockMvcBuilders.standaloneSetup(millesimeResource)
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
    public static Millesime createEntity(EntityManager em) {
        Millesime millesime = new Millesime()
            .libelle(DEFAULT_LIBELLE);
        return millesime;
    }

    @Before
    public void initTest() {
        millesime = createEntity(em);
    }

    @Test
    @Transactional
    public void createMillesime() throws Exception {
        int databaseSizeBeforeCreate = millesimeRepository.findAll().size();

        // Create the Millesime
        restMillesimeMockMvc.perform(post("/api/millesimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(millesime)))
            .andExpect(status().isCreated());

        // Validate the Millesime in the database
        List<Millesime> millesimeList = millesimeRepository.findAll();
        assertThat(millesimeList).hasSize(databaseSizeBeforeCreate + 1);
        Millesime testMillesime = millesimeList.get(millesimeList.size() - 1);
        assertThat(testMillesime.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createMillesimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = millesimeRepository.findAll().size();

        // Create the Millesime with an existing ID
        millesime.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMillesimeMockMvc.perform(post("/api/millesimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(millesime)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Millesime> millesimeList = millesimeRepository.findAll();
        assertThat(millesimeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = millesimeRepository.findAll().size();
        // set the field null
        millesime.setLibelle(null);

        // Create the Millesime, which fails.

        restMillesimeMockMvc.perform(post("/api/millesimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(millesime)))
            .andExpect(status().isBadRequest());

        List<Millesime> millesimeList = millesimeRepository.findAll();
        assertThat(millesimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMillesimes() throws Exception {
        // Initialize the database
        millesimeRepository.saveAndFlush(millesime);

        // Get all the millesimeList
        restMillesimeMockMvc.perform(get("/api/millesimes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(millesime.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getMillesime() throws Exception {
        // Initialize the database
        millesimeRepository.saveAndFlush(millesime);

        // Get the millesime
        restMillesimeMockMvc.perform(get("/api/millesimes/{id}", millesime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(millesime.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMillesime() throws Exception {
        // Get the millesime
        restMillesimeMockMvc.perform(get("/api/millesimes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMillesime() throws Exception {
        // Initialize the database
        millesimeService.save(millesime);

        int databaseSizeBeforeUpdate = millesimeRepository.findAll().size();

        // Update the millesime
        Millesime updatedMillesime = millesimeRepository.findOne(millesime.getId());
        updatedMillesime
            .libelle(UPDATED_LIBELLE);

        restMillesimeMockMvc.perform(put("/api/millesimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMillesime)))
            .andExpect(status().isOk());

        // Validate the Millesime in the database
        List<Millesime> millesimeList = millesimeRepository.findAll();
        assertThat(millesimeList).hasSize(databaseSizeBeforeUpdate);
        Millesime testMillesime = millesimeList.get(millesimeList.size() - 1);
        assertThat(testMillesime.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMillesime() throws Exception {
        int databaseSizeBeforeUpdate = millesimeRepository.findAll().size();

        // Create the Millesime

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMillesimeMockMvc.perform(put("/api/millesimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(millesime)))
            .andExpect(status().isCreated());

        // Validate the Millesime in the database
        List<Millesime> millesimeList = millesimeRepository.findAll();
        assertThat(millesimeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMillesime() throws Exception {
        // Initialize the database
        millesimeService.save(millesime);

        int databaseSizeBeforeDelete = millesimeRepository.findAll().size();

        // Get the millesime
        restMillesimeMockMvc.perform(delete("/api/millesimes/{id}", millesime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Millesime> millesimeList = millesimeRepository.findAll();
        assertThat(millesimeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Millesime.class);
        Millesime millesime1 = new Millesime();
        millesime1.setId(1L);
        Millesime millesime2 = new Millesime();
        millesime2.setId(millesime1.getId());
        assertThat(millesime1).isEqualTo(millesime2);
        millesime2.setId(2L);
        assertThat(millesime1).isNotEqualTo(millesime2);
        millesime1.setId(null);
        assertThat(millesime1).isNotEqualTo(millesime2);
    }
}
