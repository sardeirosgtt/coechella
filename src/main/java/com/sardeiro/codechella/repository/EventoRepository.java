package com.sardeiro.codechella.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.sardeiro.codechella.model.Evento;
import com.sardeiro.codechella.model.TipoEvento;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento,Long> {

    Flux<Evento> findByTipo(TipoEvento tipoEvento);
    
}
