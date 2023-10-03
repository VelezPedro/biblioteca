/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.biblioteca.servicio;

import com.example.biblioteca.entidades.Autor;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.repository.AutorRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tecno
 */
@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        validar(nombre);
        Autor autor=new Autor();
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
    }
    
    public List<Autor> listarAutores(){
    List<Autor> autores = new ArrayList();
    autores=autorRepositorio.findAll();
    
    return autores;
    }
    
    public void modificarAutor(String nombre ,String Id) throws MiException{
        validar(nombre);
        
        Optional<Autor> respuesta=autorRepositorio.findById(Id);
        
        if (respuesta.isPresent()) {
            Autor autor=respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }
    
    public void validar(String nombre) throws MiException{
        if (nombre==null || nombre.isEmpty()) {
            throw new MiException("El nombre no pueden estar vacios");
        }
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
}
