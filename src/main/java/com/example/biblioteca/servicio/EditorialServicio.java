/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.biblioteca.servicio;

import com.example.biblioteca.entidades.Editorial;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.repository.EditorialRepositorio;
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
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        validar(nombre);
        Editorial editorial=new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialRepositorio.save(editorial);
    }
    
    public List<Editorial> listarEditorial(){
        List<Editorial> editoriales=new ArrayList();
        
        editoriales=editorialRepositorio.findAll();
        
        return editoriales;
    }
    
    public void modificarEditorial(String nombre, String Id) throws MiException{
        validar(nombre);
        Optional<Editorial> respuesta =editorialRepositorio.findById(Id);
        
        if (respuesta.isPresent()) {
            Editorial editorial=respuesta.get();
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
        }
    }
    
    public void validar(String nombre)throws MiException{
        if(nombre.isEmpty() || nombre ==null){
            throw new MiException("El nombre no puede estar vacio");
        }
        
    }
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }
}
