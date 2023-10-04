package com.example.biblioteca.PortalControlador;

import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.servicio.LibroServicio;
import com.example.biblioteca.servicio.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private LibroServicio libroServicio;
    

    @GetMapping("/registrar")
    public String registar(ModelMap modelo) {
        return "usuario_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String usuario, @RequestParam String contrasena) {
        try {
            usuarioServicio.crearUsuario(usuario, contrasena);
        } catch (MiException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "autor_form.html";
        }
        return "index.html";

    }
    @GetMapping("/solicitar/{isbn}")
    public String solicitar( @PathVariable Long isbn , ModelMap modelo){
        modelo.put("libro", libroServicio.getOne(isbn));
        return "solicitud.html";
    }
    
    @PostMapping("/solicitar/{isbn}")
    public String solicitar(String usuario,String contrasena,@PathVariable Long isbn,ModelMap modelo) {
      
        try {
            //usuarioServicio.corroborarUsuario(usuario, contrasena);
            usuarioServicio.addLibro(isbn, usuario);
            modelo.put("exito", "Felicidades tenes el libro pa leer");
            return "libro_list.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "libro_list.html";
        }
        
    }
    
    
}
