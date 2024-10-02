package com.sardeiro.codechella.controller;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sardeiro.codechella.dtos.EventoDto;
import com.sardeiro.codechella.service.EventoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService service;

    private final Sinks.Many<EventoDto> eventoSink;

    public EventoController(EventoService service) {
        this.service = service;
        this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterTodos(){
        return service.obterTodos();
    }

    @GetMapping(value ="/categoria/{tipo}" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterPorTipo(@PathVariable String tipo){
        return Flux.merge(service.obterPorTipo(tipo),eventoSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }

    @GetMapping("/{id}")
    public Mono<EventoDto> obterPorId(@PathVariable Long id){
        return service.obterPorId(id);
    }

    @PostMapping()
    public Mono<EventoDto> cadastrar(@RequestBody EventoDto dto) {
        return service.cadastrar(dto)
                .doOnSuccess(e -> eventoSink.tryEmitNext(e));
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id){
        return service.excluir(id);
    }
    
}
