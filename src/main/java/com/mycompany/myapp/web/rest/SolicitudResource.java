package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SolicitudService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.SolicitudDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Solicitud.
 */
@RestController
@RequestMapping("/api")
public class SolicitudResource {

    private final Logger log = LoggerFactory.getLogger(SolicitudResource.class);

    private static final String ENTITY_NAME = "solicitud";
        
    private final SolicitudService solicitudService;

    public SolicitudResource(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    /**
     * POST  /solicituds : Create a new solicitud.
     *
     * @param solicitudDTO the solicitudDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solicitudDTO, or with status 400 (Bad Request) if the solicitud has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solicituds")
    @Timed
    public ResponseEntity<SolicitudDTO> createSolicitud(@Valid @RequestBody SolicitudDTO solicitudDTO) throws URISyntaxException {
        log.debug("REST request to save Solicitud : {}", solicitudDTO);
        if (solicitudDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new solicitud cannot already have an ID")).body(null);
        }
        SolicitudDTO result = solicitudService.save(solicitudDTO);
        return ResponseEntity.created(new URI("/api/solicituds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solicituds : Updates an existing solicitud.
     *
     * @param solicitudDTO the solicitudDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solicitudDTO,
     * or with status 400 (Bad Request) if the solicitudDTO is not valid,
     * or with status 500 (Internal Server Error) if the solicitudDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solicituds")
    @Timed
    public ResponseEntity<SolicitudDTO> updateSolicitud(@Valid @RequestBody SolicitudDTO solicitudDTO) throws URISyntaxException {
        log.debug("REST request to update Solicitud : {}", solicitudDTO);
        if (solicitudDTO.getId() == null) {
            return createSolicitud(solicitudDTO);
        }
        SolicitudDTO result = solicitudService.save(solicitudDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solicitudDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solicituds : get all the solicituds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solicituds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/solicituds")
    @Timed
    public ResponseEntity<List<SolicitudDTO>> getAllSolicituds(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Solicituds");
        Page<SolicitudDTO> page = solicitudService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solicituds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solicituds/:id : get the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solicitudDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solicituds/{id}")
    @Timed
    public ResponseEntity<SolicitudDTO> getSolicitud(@PathVariable String id) {
        log.debug("REST request to get Solicitud : {}", id);
        SolicitudDTO solicitudDTO = solicitudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solicitudDTO));
    }

    /**
     * DELETE  /solicituds/:id : delete the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solicituds/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolicitud(@PathVariable String id) {
        log.debug("REST request to delete Solicitud : {}", id);
        solicitudService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
