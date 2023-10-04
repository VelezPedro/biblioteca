
package com.example.biblioteca.repository;

import com.example.biblioteca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String>{
    
}

