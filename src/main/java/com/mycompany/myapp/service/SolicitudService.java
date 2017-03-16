package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SolicitudDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Solicitud.
 */
public interface SolicitudService {

    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save
     * @return the persisted entity
     */
    SolicitudDTO save(SolicitudDTO solicitudDTO);

    /**
     *  Get all the solicituds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SolicitudDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" solicitud.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SolicitudDTO findOne(String id);

    /**
     *  Delete the "id" solicitud.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
