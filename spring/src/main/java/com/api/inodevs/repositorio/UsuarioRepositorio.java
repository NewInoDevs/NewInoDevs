package com.api.inodevs.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Usuario;

//Repositório do Usuário para acessar dados do banco:
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
	@Query("SELECT u FROM Usuario u WHERE u.nome LIKE %?1%"
    		+ "OR u.sobrenome LIKE %?1% "
    		+ "OR u.username LIKE %?1%"
    		+ "OR u.secao LIKE %?1%"
    		+ "OR u.ativo LIKE %?1%")
    public List<Usuario> pesquisarUsuario(String palavraChave);
}
