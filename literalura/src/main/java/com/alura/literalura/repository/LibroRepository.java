package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface LibroRepository extends JpaRepository<Libro,Long>  {
    Optional<Libro> findByTituloLibro(String tituloLibro);


    @Modifying
    @Transactional
    @Query("UPDATE Libro a SET a.autorLibro.Id =  :autorId WHERE a.tituloLibro = :tituloLibro")
    void updateBookAuthorIdByTitle(@Param("autorId") Long autorId, @Param("tituloLibro") String tituloLibro);

    @NativeQuery(value = "SELECT TITULO_LIBRO FROM BOOKS WHERE AUTOR_ID = ?1")
    List<String> findByAutorId(Long autor_Id);

    List<Libro> findByLenguajeLibro(String idioma);



}
