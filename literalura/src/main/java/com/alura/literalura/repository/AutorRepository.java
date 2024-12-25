package com.alura.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;
import java.util.Optional;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAutorContainsIgnoreCase(String nombreAutor);
    Optional<Autor> findById(Long iD_autor);
    @NativeQuery(value = "SELECT * FROM AUTHORS WHERE FECHA_FALLECIDO >= ?1")
    List<Autor> findAutoByFechaFallecido(Integer year);




}
