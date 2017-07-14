package info.kapable.caveapp.web.rest;

import info.kapable.caveapp.CaveappApp;

import info.kapable.caveapp.domain.Vin;
import info.kapable.caveapp.repository.VinRepository;
import info.kapable.caveapp.service.VinService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VinResource REST controller.
 *
 * @see VinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaveappApp.class)
public class VinResourceIntTest {

    private static final String DEFAULT_APPELLATION = "AAAAAAAAAA";
    private static final String UPDATED_APPELLATION = "BBBBBBBBBB";

    private static final String DEFAULT_CUVEE = "AAAAAAAAAA";
    private static final String UPDATED_CUVEE = "BBBBBBBBBB";

    private static final String DEFAULT_CEPAGE = "AAAAAAAAAA";
    private static final String UPDATED_CEPAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    private static final String DEFAULT_TAUX_ALCOOL = "AAAAAAAAAA";
    private static final String UPDATED_TAUX_ALCOOL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_BARE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BARE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE_DEBUT = 1;
    private static final Integer UPDATED_ANNEE_DEBUT = 2;

    private static final Integer DEFAULT_ANNEE_FIN = 1;
    private static final Integer UPDATED_ANNEE_FIN = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO_ETIQUETTE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_ETIQUETTE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_ETIQUETTE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_ETIQUETTE_CONTENT_TYPE = "image/png";

    @Autowired
    private VinRepository vinRepository;

    @Autowired
    private VinService vinService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVinMockMvc;

    private Vin vin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VinResource vinResource = new VinResource(vinService);
        this.restVinMockMvc = MockMvcBuilders.standaloneSetup(vinResource)
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
    public static Vin createEntity(EntityManager em) {
        Vin vin = new Vin()
            .appellation(DEFAULT_APPELLATION)
            .cuvee(DEFAULT_CUVEE)
            .cepage(DEFAULT_CEPAGE)
            .region(DEFAULT_REGION)
            .domaine(DEFAULT_DOMAINE)
            .temperature(DEFAULT_TEMPERATURE)
            .tauxAlcool(DEFAULT_TAUX_ALCOOL)
            .codeBare(DEFAULT_CODE_BARE)
            .anneeDebut(DEFAULT_ANNEE_DEBUT)
            .anneeFin(DEFAULT_ANNEE_FIN)
            .description(DEFAULT_DESCRIPTION)
            .photoEtiquette(DEFAULT_PHOTO_ETIQUETTE)
            .photoEtiquetteContentType(DEFAULT_PHOTO_ETIQUETTE_CONTENT_TYPE);
        return vin;
    }

    @Before
    public void initTest() {
        vin = createEntity(em);
    }

    @Test
    @Transactional
    public void createVin() throws Exception {
        int databaseSizeBeforeCreate = vinRepository.findAll().size();

        // Create the Vin
        restVinMockMvc.perform(post("/api/vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vin)))
            .andExpect(status().isCreated());

        // Validate the Vin in the database
        List<Vin> vinList = vinRepository.findAll();
        assertThat(vinList).hasSize(databaseSizeBeforeCreate + 1);
        Vin testVin = vinList.get(vinList.size() - 1);
        assertThat(testVin.getAppellation()).isEqualTo(DEFAULT_APPELLATION);
        assertThat(testVin.getCuvee()).isEqualTo(DEFAULT_CUVEE);
        assertThat(testVin.getCepage()).isEqualTo(DEFAULT_CEPAGE);
        assertThat(testVin.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testVin.getDomaine()).isEqualTo(DEFAULT_DOMAINE);
        assertThat(testVin.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testVin.getTauxAlcool()).isEqualTo(DEFAULT_TAUX_ALCOOL);
        assertThat(testVin.getCodeBare()).isEqualTo(DEFAULT_CODE_BARE);
        assertThat(testVin.getAnneeDebut()).isEqualTo(DEFAULT_ANNEE_DEBUT);
        assertThat(testVin.getAnneeFin()).isEqualTo(DEFAULT_ANNEE_FIN);
        assertThat(testVin.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVin.getPhotoEtiquette()).isEqualTo(DEFAULT_PHOTO_ETIQUETTE);
        assertThat(testVin.getPhotoEtiquetteContentType()).isEqualTo(DEFAULT_PHOTO_ETIQUETTE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createVinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vinRepository.findAll().size();

        // Create the Vin with an existing ID
        vin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVinMockMvc.perform(post("/api/vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Vin> vinList = vinRepository.findAll();
        assertThat(vinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAppellationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinRepository.findAll().size();
        // set the field null
        vin.setAppellation(null);

        // Create the Vin, which fails.

        restVinMockMvc.perform(post("/api/vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vin)))
            .andExpect(status().isBadRequest());

        List<Vin> vinList = vinRepository.findAll();
        assertThat(vinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVins() throws Exception {
        // Initialize the database
        vinRepository.saveAndFlush(vin);

        // Get all the vinList
        restVinMockMvc.perform(get("/api/vins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vin.getId().intValue())))
            .andExpect(jsonPath("$.[*].appellation").value(hasItem(DEFAULT_APPELLATION.toString())))
            .andExpect(jsonPath("$.[*].cuvee").value(hasItem(DEFAULT_CUVEE.toString())))
            .andExpect(jsonPath("$.[*].cepage").value(hasItem(DEFAULT_CEPAGE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].tauxAlcool").value(hasItem(DEFAULT_TAUX_ALCOOL.toString())))
            .andExpect(jsonPath("$.[*].codeBare").value(hasItem(DEFAULT_CODE_BARE.toString())))
            .andExpect(jsonPath("$.[*].anneeDebut").value(hasItem(DEFAULT_ANNEE_DEBUT)))
            .andExpect(jsonPath("$.[*].anneeFin").value(hasItem(DEFAULT_ANNEE_FIN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].photoEtiquetteContentType").value(hasItem(DEFAULT_PHOTO_ETIQUETTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoEtiquette").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_ETIQUETTE))));
    }

    @Test
    @Transactional
    public void getVin() throws Exception {
        // Initialize the database
        vinRepository.saveAndFlush(vin);

        // Get the vin
        restVinMockMvc.perform(get("/api/vins/{id}", vin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vin.getId().intValue()))
            .andExpect(jsonPath("$.appellation").value(DEFAULT_APPELLATION.toString()))
            .andExpect(jsonPath("$.cuvee").value(DEFAULT_CUVEE.toString()))
            .andExpect(jsonPath("$.cepage").value(DEFAULT_CEPAGE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.toString()))
            .andExpect(jsonPath("$.tauxAlcool").value(DEFAULT_TAUX_ALCOOL.toString()))
            .andExpect(jsonPath("$.codeBare").value(DEFAULT_CODE_BARE.toString()))
            .andExpect(jsonPath("$.anneeDebut").value(DEFAULT_ANNEE_DEBUT))
            .andExpect(jsonPath("$.anneeFin").value(DEFAULT_ANNEE_FIN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.photoEtiquetteContentType").value(DEFAULT_PHOTO_ETIQUETTE_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoEtiquette").value(Base64Utils.encodeToString(DEFAULT_PHOTO_ETIQUETTE)));
    }

    @Test
    @Transactional
    public void getNonExistingVin() throws Exception {
        // Get the vin
        restVinMockMvc.perform(get("/api/vins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVin() throws Exception {
        // Initialize the database
        vinService.save(vin);

        int databaseSizeBeforeUpdate = vinRepository.findAll().size();

        // Update the vin
        Vin updatedVin = vinRepository.findOne(vin.getId());
        updatedVin
            .appellation(UPDATED_APPELLATION)
            .cuvee(UPDATED_CUVEE)
            .cepage(UPDATED_CEPAGE)
            .region(UPDATED_REGION)
            .domaine(UPDATED_DOMAINE)
            .temperature(UPDATED_TEMPERATURE)
            .tauxAlcool(UPDATED_TAUX_ALCOOL)
            .codeBare(UPDATED_CODE_BARE)
            .anneeDebut(UPDATED_ANNEE_DEBUT)
            .anneeFin(UPDATED_ANNEE_FIN)
            .description(UPDATED_DESCRIPTION)
            .photoEtiquette(UPDATED_PHOTO_ETIQUETTE)
            .photoEtiquetteContentType(UPDATED_PHOTO_ETIQUETTE_CONTENT_TYPE);

        restVinMockMvc.perform(put("/api/vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVin)))
            .andExpect(status().isOk());

        // Validate the Vin in the database
        List<Vin> vinList = vinRepository.findAll();
        assertThat(vinList).hasSize(databaseSizeBeforeUpdate);
        Vin testVin = vinList.get(vinList.size() - 1);
        assertThat(testVin.getAppellation()).isEqualTo(UPDATED_APPELLATION);
        assertThat(testVin.getCuvee()).isEqualTo(UPDATED_CUVEE);
        assertThat(testVin.getCepage()).isEqualTo(UPDATED_CEPAGE);
        assertThat(testVin.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testVin.getDomaine()).isEqualTo(UPDATED_DOMAINE);
        assertThat(testVin.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testVin.getTauxAlcool()).isEqualTo(UPDATED_TAUX_ALCOOL);
        assertThat(testVin.getCodeBare()).isEqualTo(UPDATED_CODE_BARE);
        assertThat(testVin.getAnneeDebut()).isEqualTo(UPDATED_ANNEE_DEBUT);
        assertThat(testVin.getAnneeFin()).isEqualTo(UPDATED_ANNEE_FIN);
        assertThat(testVin.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVin.getPhotoEtiquette()).isEqualTo(UPDATED_PHOTO_ETIQUETTE);
        assertThat(testVin.getPhotoEtiquetteContentType()).isEqualTo(UPDATED_PHOTO_ETIQUETTE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVin() throws Exception {
        int databaseSizeBeforeUpdate = vinRepository.findAll().size();

        // Create the Vin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVinMockMvc.perform(put("/api/vins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vin)))
            .andExpect(status().isCreated());

        // Validate the Vin in the database
        List<Vin> vinList = vinRepository.findAll();
        assertThat(vinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVin() throws Exception {
        // Initialize the database
        vinService.save(vin);

        int databaseSizeBeforeDelete = vinRepository.findAll().size();

        // Get the vin
        restVinMockMvc.perform(delete("/api/vins/{id}", vin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vin> vinList = vinRepository.findAll();
        assertThat(vinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vin.class);
        Vin vin1 = new Vin();
        vin1.setId(1L);
        Vin vin2 = new Vin();
        vin2.setId(vin1.getId());
        assertThat(vin1).isEqualTo(vin2);
        vin2.setId(2L);
        assertThat(vin1).isNotEqualTo(vin2);
        vin1.setId(null);
        assertThat(vin1).isNotEqualTo(vin2);
    }
}
