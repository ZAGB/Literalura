package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.Optional;


@Entity
@Table(name="books")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer idLibro;
    private String tituloLibro;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_Id", referencedColumnName = "Id")
    private Autor  autorLibro;
    private String lenguajeLibro;
    private Integer descargasLibro;


    public Libro(DatosLibro d, Autor a) {
        this.idLibro = d.idLibro();
        this.tituloLibro = d.tituloLibro();
        this.autorLibro = a;
        this.lenguajeLibro = d.idiomas().getFirst();
        this.descargasLibro = d.descargasLibro();
    }

    public Libro(DatosLibro d) {
        this.idLibro = d.idLibro();
        this.tituloLibro = d.tituloLibro();
        this.lenguajeLibro = d.idiomas().getFirst();
        this.descargasLibro = d.descargasLibro();
    }

    public Libro() {
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public void setLenguajeLibro(String lenguajeLibro) {
        this.lenguajeLibro = lenguajeLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public Autor getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(Autor autorLibro) {
        this.autorLibro = autorLibro;
    }

    public Integer getDescargasLibro() {
        return descargasLibro;
    }

    public void setDescargasLibro(Integer descargasLibro) {
        this.descargasLibro = descargasLibro;
    }

    @Override
    public String toString() {
        return "-- Libro datillo -- " + "\n" +
                "Titulo: " + tituloLibro + "\n" +
                "Autor: " + autorLibro.getAutor() + "\n" +
                "Idioma: " + lenguajeLibro + "\n" +
                "Descargas completadas para el Libro: " + descargasLibro + "\n" + "\n" ;

    }
}
