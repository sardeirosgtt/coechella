package com.sardeiro.codechella.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.sardeiro.codechella.dtos.EventoDto;
import com.sardeiro.codechella.model.TipoEvento;
import com.sardeiro.codechella.repository.EventoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    public Flux<EventoDto> obterTodos(){
        return repository.findAll()
                .map(EventoDto::toDto);
    }

    public Mono<EventoDto> obterPorId(Long id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDto::toDto);
    }

    public Mono<EventoDto> cadastrar(EventoDto dto) {
        return repository.save(dto.toEntity())
                .map(EventoDto::toDto);
    }

    public Mono<Void> excluir(Long id) {
        return repository.deleteById(id);
    }

    public Flux<EventoDto> obterPorTipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return repository.findByTipo(tipoEvento)
                .map(EventoDto::toDto);
    }

}
