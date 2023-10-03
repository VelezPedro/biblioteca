/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.biblioteca.servicio;

import com.example.biblioteca.entidades.Libro;
import com.example.biblioteca.entidades.Usuario;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.repository.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tecno
 */
@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void crearUsuario(String usuario, String contrasena) throws MiException{
        validar(usuario,contrasena);
        
        Usuario user=new Usuario();
        user.setUsuario(usuario);
        user.setContrasena(contrasena);
        
        usuarioRepositorio.save(user);
        
    }
    
    
     public void validar(String usuario , String contrasena) throws MiException{
        if (usuario==null || usuario.isEmpty()) {
            throw new MiException("El usuario no pueden estar vacios");
        }
        if (contrasena==null || contrasena.isEmpty()) {
            throw new MiException("La contrasena no pueden estar vacia");
        }  
    }
     
     @Transactional
     public void addLibro(Libro libro,Usuario usuario,List<Libro> lista) throws MiException{
         if (libro.getEjemplares()>1) {
             throw new MiException("No hay mas ejemplares de "+libro.getTitulo());
         }
         //si no es al add es otra cosa, como put.
         lista.add(libro);
         
     }
}
