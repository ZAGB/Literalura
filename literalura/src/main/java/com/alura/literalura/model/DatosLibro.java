package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro( @JsonAlias("id") Integer idLibro,
                          @JsonAlias("title") String tituloLibro,
                          @JsonAlias("authors") List<DatosAutores> autorinfo,
                          @JsonAlias("languages") List<String> idiomas,
                          @JsonAlias("download_count") Integer descargasLibro)
{
}
