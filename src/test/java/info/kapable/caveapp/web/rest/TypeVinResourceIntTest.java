package info.kapable.caveapp.web.rest;

import info.kapable.caveapp.CaveappApp;

import info.kapable.caveapp.domain.TypeVin;
import info.kapable.caveapp.repository.TypeVinRepository;
import info.kapable.caveapp.service.TypeVinService;
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
 * Test class for the TypeVinResource REST controller.
 *
 * @see TypeVinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaveappApp.class)
public class TypeVinResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeVinRepository typeVinRepository;

    @Autowired
    private TypeVinService typeVinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeVinMockMvc;

    private TypeVin typeVin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TypeVinResource typeVinResource = new TypeVinResource(typeVinService);
        this.restTypeVinMockMvc = MockMvcBuilders.standaloneSetup(typeVinResource)
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
    public static TypeVin createEntity(EntityManager em) {
        TypeVin typeVin = new TypeVin()
            .libelle(DEFAULT_LIBELLE);
        return typeVin;
    }

    @Before
    public void initTest() {
        typeVin = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeVin() throws Exception {
        int databaseSizeBeforeCreate = typeVinRepository.findAll().size();

        // Create the TypeVin
        restTypeVinMockMvc.perform(post("/api/type-vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVin)))
            .andExpect(status().isCreated());

        // Validate the TypeVin in the database
        List<TypeVin> typeVinList = typeVinRepository.findAll();
        assertThat(typeVinList).hasSize(databaseSizeBeforeCreate + 1);
        TypeVin testTypeVin = typeVinList.get(typeVinList.size() - 1);
        assertThat(testTypeVin.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeVinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeVinRepository.findAll().size();

        // Create the TypeVin with an existing ID
        typeVin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeVinMockMvc.perform(post("/api/type-vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TypeVin> typeVinList = typeVinRepository.findAll();
        assertThat(typeVinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeVinRepository.findAll().size();
        // set the field null
        typeVin.setLibelle(null);

        // Create the TypeVin, which fails.

        restTypeVinMockMvc.perform(post("/api/type-vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVin)))
            .andExpect(status().isBadRequest());

        List<TypeVin> typeVinList = typeVinRepository.findAll();
        assertThat(typeVinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeVins() throws Exception {
        // Initialize the database
        typeVinRepository.saveAndFlush(typeVin);

        // Get all the typeVinList
        restTypeVinMockMvc.perform(get("/api/type-vins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeVin.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getTypeVin() throws Exception {
        // Initialize the database
        typeVinRepository.saveAndFlush(typeVin);

        // Get the typeVin
        restTypeVinMockMvc.perform(get("/api/type-vins/{id}", typeVin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeVin.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeVin() throws Exception {
        // Get the typeVin
        restTypeVinMockMvc.perform(get("/api/type-vins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeVin() throws Exception {
        // Initialize the database
        typeVinService.save(typeVin);

        int databaseSizeBeforeUpdate = typeVinRepository.findAll().size();

        // Update the typeVin
        TypeVin updatedTypeVin = typeVinRepository.findOne(typeVin.getId());
        updatedTypeVin
            .libelle(UPDATED_LIBELLE);

        restTypeVinMockMvc.perform(put("/api/type-vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeVin)))
            .andExpect(status().isOk());

        // Validate the TypeVin in the database
        List<TypeVin> typeVinList = typeVinRepository.findAll();
        assertThat(typeVinList).hasSize(databaseSizeBeforeUpdate);
        TypeVin testTypeVin = typeVinList.get(typeVinList.size() - 1);
        assertThat(testTypeVin.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeVin() throws Exception {
        int databaseSizeBeforeUpdate = typeVinRepository.findAll().size();

        // Create the TypeVin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeVinMockMvc.perform(put("/api/type-vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeVin)))
            .andExpect(status().isCreated());

        // Validate the TypeVin in the database
        List<TypeVin> typeVinList = typeVinRepository.findAll();
        assertThat(typeVinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeVin() throws Exception {
        // Initialize the database
        typeVinService.save(typeVin);

        int databaseSizeBeforeDelete = typeVinRepository.findAll().size();

        // Get the typeVin
        restTypeVinMockMvc.perform(delete("/api/type-vins/{id}", typeVin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeVin> typeVinList = typeVinRepository.findAll();
        assertThat(typeVinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeVin.class);
        TypeVin typeVin1 = new TypeVin();
        typeVin1.setId(1L);
        TypeVin typeVin2 = new TypeVin();
        typeVin2.setId(typeVin1.getId());
        assertThat(typeVin1).isEqualTo(typeVin2);
        typeVin2.setId(2L);
        assertThat(typeVin1).isNotEqualTo(typeVin2);
        typeVin1.setId(null);
        assertThat(typeVin1).isNotEqualTo(typeVin2);
    }
}
