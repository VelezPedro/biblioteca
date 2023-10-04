package com.example.biblioteca.servicio;

import com.example.biblioteca.entidades.Libro;
import com.example.biblioteca.entidades.Usuario;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.repository.LibroRepositorio;
import com.example.biblioteca.repository.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void crearUsuario(String usuario, String contrasena) throws MiException {
        validar(usuario, contrasena);

        Usuario user = new Usuario();
        user.setUsuario(usuario);
        user.setContrasena(contrasena);

        usuarioRepositorio.save(user);

    }

    public void validar(String usuario, String contrasena) throws MiException {
        if (usuario == null || usuario.isEmpty()) {
            throw new MiException("El usuario no pueden estar vacios");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new MiException("La contrasena no pueden estar vacia");
        }
    }

    //ISBN por que es el id de libro
     public void addLibro(Long isbn,String usuario) throws MiException {
         Optional<Libro> respuesta=libroRepositorio.findById(isbn);
         Optional<Usuario> respuestaUsuario=usuarioRepositorio.findById(usuario);
         
         if (respuesta.isPresent() && respuestaUsuario.isPresent()) {
             Libro libro=respuesta.get();
             Usuario user =respuestaUsuario.get();
             List<Libro> librosLista =user.getLibros();
             librosLista.add(libro);
             modificarUsuario(user.getUsuario(), user.getContrasena(), librosLista);
         }
         
     }
    public void modificarUsuario(String usuario, String contrasena, List<Libro> libros) throws MiException {
        validar(usuario, contrasena);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(usuario);
        if (respuesta.isPresent()) {
            Usuario nuevoUsuario = respuesta.get();
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setLibros(libros);
            usuarioRepositorio.save(nuevoUsuario);
        }
    }

    public Usuario getOne(String usuario) {
        return usuarioRepositorio.getOne(usuario);
    }

    public void corroborarUsuario(String usuario, String contrasena) throws MiException {
        validar(usuario, contrasena);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(usuario);
        if (respuesta.isPresent()) {
            if (respuesta.get().getContrasena()!= contrasena ) {
                throw new MiException("Contraseña incorrecta");
            }
           
        }else{
            throw new MiException("Usuario no encontrado");
        }
        
    }
}
