package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;

import com.mycompany.myapp.domain.Solicitud;
import com.mycompany.myapp.repository.SolicitudRepository;
import com.mycompany.myapp.service.SolicitudService;
import com.mycompany.myapp.service.dto.SolicitudDTO;
import com.mycompany.myapp.service.mapper.SolicitudMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Origenes;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.domain.enumeration.Interno,externo;
/**
 * Test class for the SolicitudResource REST controller.
 *
 * @see SolicitudResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class SolicitudResourceIntTest {

    private static final Integer DEFAULT_NUMERO_SOLICITUD = 1;
    private static final Integer UPDATED_NUMERO_SOLICITUD = 2;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ACTO_CERTIFICAR = "AAAAAAAAAA";
    private static final String UPDATED_ACTO_CERTIFICAR = "BBBBBBBBBB";

    private static final Origenes DEFAULT_ORIGEN = Origenes.Acajete;
    private static final Origenes UPDATED_ORIGEN = Origenes.Acatlan;

    private static final Estatus DEFAULT_ESTATUS = Estatus.registrada;
    private static final Estatus UPDATED_ESTATUS = Estatus.enproceso;

    private static final Boolean DEFAULT_PREVENCION = false;
    private static final Boolean UPDATED_PREVENCION = true;

    private static final Interno,externo DEFAULT_TIPORESPONSABLE = Interno,externo.interno;
    private static final Interno,externo UPDATED_TIPORESPONSABLE = Interno,externo.externo;

    private static final String DEFAULT_NOMBRE_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_RESPONSABLE = "BBBBBBBBBB";

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSolicitudMockMvc;

    private Solicitud solicitud;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SolicitudResource solicitudResource = new SolicitudResource(solicitudService);
        this.restSolicitudMockMvc = MockMvcBuilders.standaloneSetup(solicitudResource)
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
    public static Solicitud createEntity() {
        Solicitud solicitud = new Solicitud()
            .numero_solicitud(DEFAULT_NUMERO_SOLICITUD)
            .fecha(DEFAULT_FECHA)
            .hora(DEFAULT_HORA)
            .acto_certificar(DEFAULT_ACTO_CERTIFICAR)
            .origen(DEFAULT_ORIGEN)
            .estatus(DEFAULT_ESTATUS)
            .prevencion(DEFAULT_PREVENCION)
            .tiporesponsable(DEFAULT_TIPORESPONSABLE)
            .nombre_responsable(DEFAULT_NOMBRE_RESPONSABLE);
        return solicitud;
    }

    @Before
    public void initTest() {
        solicitudRepository.deleteAll();
        solicitud = createEntity();
    }

    @Test
    public void createSolicitud() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().size();

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);
        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isCreated());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate + 1);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getNumero_solicitud()).isEqualTo(DEFAULT_NUMERO_SOLICITUD);
        assertThat(testSolicitud.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testSolicitud.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testSolicitud.getActo_certificar()).isEqualTo(DEFAULT_ACTO_CERTIFICAR);
        assertThat(testSolicitud.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
        assertThat(testSolicitud.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testSolicitud.isPrevencion()).isEqualTo(DEFAULT_PREVENCION);
        assertThat(testSolicitud.getTiporesponsable()).isEqualTo(DEFAULT_TIPORESPONSABLE);
        assertThat(testSolicitud.getNombre_responsable()).isEqualTo(DEFAULT_NOMBRE_RESPONSABLE);
    }

    @Test
    public void createSolicitudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().size();

        // Create the Solicitud with an existing ID
        solicitud.setId("existing_id");
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNumero_solicitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setNumero_solicitud(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setFecha(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActo_certificarIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setActo_certificar(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOrigenIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setOrigen(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setEstatus(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTiporesponsableIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setTiporesponsable(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNombre_responsableIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitudRepository.findAll().size();
        // set the field null
        solicitud.setNombre_responsable(null);

        // Create the Solicitud, which fails.
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSolicituds() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud);

        // Get all the solicitudList
        restSolicitudMockMvc.perform(get("/api/solicituds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitud.getId())))
            .andExpect(jsonPath("$.[*].numero_solicitud").value(hasItem(DEFAULT_NUMERO_SOLICITUD)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(sameInstant(DEFAULT_HORA))))
            .andExpect(jsonPath("$.[*].acto_certificar").value(hasItem(DEFAULT_ACTO_CERTIFICAR.toString())))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN.toString())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].prevencion").value(hasItem(DEFAULT_PREVENCION.booleanValue())))
            .andExpect(jsonPath("$.[*].tiporesponsable").value(hasItem(DEFAULT_TIPORESPONSABLE.toString())))
            .andExpect(jsonPath("$.[*].nombre_responsable").value(hasItem(DEFAULT_NOMBRE_RESPONSABLE.toString())));
    }

    @Test
    public void getSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud);

        // Get the solicitud
        restSolicitudMockMvc.perform(get("/api/solicituds/{id}", solicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solicitud.getId()))
            .andExpect(jsonPath("$.numero_solicitud").value(DEFAULT_NUMERO_SOLICITUD))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.hora").value(sameInstant(DEFAULT_HORA)))
            .andExpect(jsonPath("$.acto_certificar").value(DEFAULT_ACTO_CERTIFICAR.toString()))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN.toString()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.prevencion").value(DEFAULT_PREVENCION.booleanValue()))
            .andExpect(jsonPath("$.tiporesponsable").value(DEFAULT_TIPORESPONSABLE.toString()))
            .andExpect(jsonPath("$.nombre_responsable").value(DEFAULT_NOMBRE_RESPONSABLE.toString()));
    }

    @Test
    public void getNonExistingSolicitud() throws Exception {
        // Get the solicitud
        restSolicitudMockMvc.perform(get("/api/solicituds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud);
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().size();

        // Update the solicitud
        Solicitud updatedSolicitud = solicitudRepository.findOne(solicitud.getId());
        updatedSolicitud
            .numero_solicitud(UPDATED_NUMERO_SOLICITUD)
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .acto_certificar(UPDATED_ACTO_CERTIFICAR)
            .origen(UPDATED_ORIGEN)
            .estatus(UPDATED_ESTATUS)
            .prevencion(UPDATED_PREVENCION)
            .tiporesponsable(UPDATED_TIPORESPONSABLE)
            .nombre_responsable(UPDATED_NOMBRE_RESPONSABLE);
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(updatedSolicitud);

        restSolicitudMockMvc.perform(put("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isOk());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getNumero_solicitud()).isEqualTo(UPDATED_NUMERO_SOLICITUD);
        assertThat(testSolicitud.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testSolicitud.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testSolicitud.getActo_certificar()).isEqualTo(UPDATED_ACTO_CERTIFICAR);
        assertThat(testSolicitud.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testSolicitud.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testSolicitud.isPrevencion()).isEqualTo(UPDATED_PREVENCION);
        assertThat(testSolicitud.getTiporesponsable()).isEqualTo(UPDATED_TIPORESPONSABLE);
        assertThat(testSolicitud.getNombre_responsable()).isEqualTo(UPDATED_NOMBRE_RESPONSABLE);
    }

    @Test
    public void updateNonExistingSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().size();

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolicitudMockMvc.perform(put("/api/solicituds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isCreated());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud);
        int databaseSizeBeforeDelete = solicitudRepository.findAll().size();

        // Get the solicitud
        restSolicitudMockMvc.perform(delete("/api/solicituds/{id}", solicitud.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solicitud.class);
    }
}
