/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.biblioteca.PortalControlador;

import com.example.biblioteca.entidades.Editorial;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.servicio.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Tecno
 */
@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial fue creada");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "editorial_form.html";
        }
        return "index.html";
    }

    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditorial();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
    
    
    //estoy boludeando con esto 
    @GetMapping("/modificar/{id}")
    public String modificar (@PathVariable String id ,ModelMap modelo){
        modelo.put("editorial",editorialServicio.getOne(id));
        return "editorial_modificar.html";
    }
    //estoy boludeando con esto 
    @PostMapping("/modificar/{id}")
    public String modificar (@PathVariable String id ,String nombre,ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(nombre, id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }
}
