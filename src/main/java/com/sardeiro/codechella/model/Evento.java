package com.sardeiro.codechella.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Table("eventos")
@Data
public class Evento {

    @Id
    private Long id;

    private TipoEvento tipo;
    private String nome;
    private LocalDate data;
    private String descricao;
    
}
