 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.biblioteca.servicio;

import com.example.biblioteca.entidades.Autor;
import com.example.biblioteca.entidades.Editorial;
import com.example.biblioteca.entidades.Libro;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.repository.AutorRepositorio;
import com.example.biblioteca.repository.EditorialRepositorio;
import com.example.biblioteca.repository.LibroRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tecno
 */
@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo , Integer ejemplares,String IdAutor, String IdEditorial) throws MiException{
        validar(isbn, titulo, IdAutor, IdEditorial, ejemplares);
        
        Autor autor=autorRepositorio.findById(IdAutor).get();
        Editorial editorial=editorialRepositorio.findById(IdEditorial).get();
        
        Libro libro =new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);//save;Guarda una entidad , viene intrinseco con LibroRepositorio.
    }
    
    public List<Libro> listarLibro(){
        List<Libro> libros= new ArrayList();
        
        libros=libroRepositorio.findAll();
        
        return libros;
    }
    
    public void modificarLibro(Long isbn,String titulo,String IdAutor,String IdEditorial,Integer ejemplares) throws MiException{
        validar(isbn, titulo, IdAutor, IdEditorial, ejemplares);
        
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor=autorRepositorio.findById(IdAutor);
        Optional<Editorial> respuestaEditorial=editorialRepositorio.findById(IdEditorial);
        
        Autor autor=new Autor();
        Editorial editorial=new Editorial();
        
        if (respuestaAutor.isPresent()) {
            autor=respuestaAutor.get();
        }
        
        if (respuestaEditorial.isPresent()) {
            editorial=respuestaEditorial.get();
        }
        
        if (respuesta.isPresent()) {
            Libro libro =new Libro();
            
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);
        }
    }
    
    private void validar(Long isbn,String titulo,String IdAutor,String IdEditorial,Integer ejemplares)throws MiException{
        if (isbn==null) {
            throw new MiException("El isbn no puede estar vacio");
        }
        if (ejemplares==null) {
            throw new MiException("Los ejemplares no pueden estar vacios");
        }
        if (titulo.isEmpty() || titulo==null) {
            throw new MiException("El titulo no puede estar vacio.");
        }
         if (IdAutor==null ||IdAutor.isEmpty()) {
            throw new MiException("El IdAutor no puede estar vacio");
        }
        if (IdEditorial.isEmpty() || IdEditorial==null) {
            throw new MiException("El IdEditorial no puede estar vacio.");
        }
    }
    //estoy boludeando con esto 
    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }
}
