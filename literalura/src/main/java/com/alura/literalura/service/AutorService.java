package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final LibroRepository lRepository;
    private final AutorRepository aRepository;

    public AutorService(LibroRepository lRepository, AutorRepository aRepository) {
        this.lRepository = lRepository;
        this.aRepository = aRepository;
    }

    @Transactional
    public void updateBookAuthorIdByTitle(Long autorId, String tituloLibro) {
        Libro libro = lRepository.findByTituloLibro(tituloLibro)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Autor autor = aRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        libro.setAutorLibro(autor);
        lRepository.save(libro);
        System.out.println(libro.toString());
    }



}
