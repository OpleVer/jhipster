package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Solicitud;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Solicitud entity.
 */
@SuppressWarnings("unused")
public interface SolicitudRepository extends MongoRepository<Solicitud,String> {

}
