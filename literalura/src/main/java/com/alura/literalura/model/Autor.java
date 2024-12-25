package com.alura.literalura.model;


import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="authors")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String autor;
    private Integer fechaNacimiento;
    private Integer fechaFallecido;
    @OneToMany(mappedBy = "autorLibro", cascade = CascadeType.ALL)
    private List<Libro> libro;

    public Autor(DatosAutores a) {
        this.autor = a.autor();
        this.fechaNacimiento = a.fechaNacimiento();
        this.fechaFallecido = a.fechaFallecido();

    }

    public Autor() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecido() {
        return fechaFallecido;
    }

    public void setFechaFallecido(Integer fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    @Override
    public String toString() {
        return
                "\n" +
                "  Nombre: " + autor + "\n" +
                "  Fecha de Nacimiento: " + fechaNacimiento + "\n" +
                "  Fecha de Fallecido: " + fechaFallecido;
    }
}
