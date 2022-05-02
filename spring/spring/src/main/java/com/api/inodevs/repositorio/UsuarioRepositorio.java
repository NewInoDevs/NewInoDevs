package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Usuario;

//Repositório do Usuário para acessar dados do banco:
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
}
