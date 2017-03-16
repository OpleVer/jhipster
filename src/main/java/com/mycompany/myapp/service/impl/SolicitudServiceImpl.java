package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SolicitudService;
import com.mycompany.myapp.domain.Solicitud;
import com.mycompany.myapp.repository.SolicitudRepository;
import com.mycompany.myapp.service.dto.SolicitudDTO;
import com.mycompany.myapp.service.mapper.SolicitudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Solicitud.
 */
@Service
public class SolicitudServiceImpl implements SolicitudService{

    private final Logger log = LoggerFactory.getLogger(SolicitudServiceImpl.class);
    
    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    public SolicitudServiceImpl(SolicitudRepository solicitudRepository, SolicitudMapper solicitudMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        log.debug("Request to save Solicitud : {}", solicitudDTO);
        Solicitud solicitud = solicitudMapper.solicitudDTOToSolicitud(solicitudDTO);
        solicitud = solicitudRepository.save(solicitud);
        SolicitudDTO result = solicitudMapper.solicitudToSolicitudDTO(solicitud);
        return result;
    }

    /**
     *  Get all the solicituds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<SolicitudDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solicituds");
        Page<Solicitud> result = solicitudRepository.findAll(pageable);
        return result.map(solicitud -> solicitudMapper.solicitudToSolicitudDTO(solicitud));
    }

    /**
     *  Get one solicitud by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public SolicitudDTO findOne(String id) {
        log.debug("Request to get Solicitud : {}", id);
        Solicitud solicitud = solicitudRepository.findOne(id);
        SolicitudDTO solicitudDTO = solicitudMapper.solicitudToSolicitudDTO(solicitud);
        return solicitudDTO;
    }

    /**
     *  Delete the  solicitud by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Solicitud : {}", id);
        solicitudRepository.delete(id);
    }
}
