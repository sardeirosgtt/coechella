package com.sardeiro.codechella.dtos;

import java.time.LocalDate;
import com.sardeiro.codechella.model.Evento;
import com.sardeiro.codechella.model.TipoEvento;

public record EventoDto(Long id,
TipoEvento tipo,
String nome,
LocalDate data,
String descricao) {

    public static EventoDto toDto(Evento evento){
        return new EventoDto(evento.getId(), evento.getTipo(), evento.getNome(), evento.getData(), evento.getDescricao());
    }

    public Evento toEntity(){
        Evento evento = new Evento();
        evento.setId(this.id);
        evento.setTipo(this.tipo);
        evento.setNome(this.nome);
        evento.setData(this.data);
        evento.setDescricao(this.descricao);
        return evento;
    }
}
