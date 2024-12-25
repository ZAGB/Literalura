package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(@JsonAlias("name") String autor,
                           @JsonAlias("birth_year") Integer fechaNacimiento,
                           @JsonAlias("death_year") Integer fechaFallecido
) {
}
