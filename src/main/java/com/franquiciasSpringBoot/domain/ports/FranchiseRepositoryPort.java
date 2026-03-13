package com.franquiciasSpringBoot.domain.ports;

import com.franquiciasSpringBoot.domain.models.Franchise;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface FranchiseRepositoryPort {

    // Para guardar o actualizar una franquicia
    Mono<Franchise> save(Franchise franchise);

    // Para buscar una franquicia puntual
    Mono<Franchise> findById(String id);

    // Para listar todas (útil para pruebas)
    Flux<Franchise> findAll();

    // Para el requerimiento de eliminar productos o modificar stock [cite: 9, 10]
    // Generalmente guardaremos la franquicia actualizada, pero este puerto
    // asegura que podamos recuperar la entidad completa para manipularla.
}
