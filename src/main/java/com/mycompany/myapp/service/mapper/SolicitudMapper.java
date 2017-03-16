package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SolicitudDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Solicitud and its DTO SolicitudDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicitudMapper {

    SolicitudDTO solicitudToSolicitudDTO(Solicitud solicitud);

    List<SolicitudDTO> solicitudsToSolicitudDTOs(List<Solicitud> solicituds);

    Solicitud solicitudDTOToSolicitud(SolicitudDTO solicitudDTO);

    List<Solicitud> solicitudDTOsToSolicituds(List<SolicitudDTO> solicitudDTOs);
}
