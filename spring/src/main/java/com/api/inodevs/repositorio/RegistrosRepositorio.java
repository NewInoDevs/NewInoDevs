package com.api.inodevs.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Registros;

//Repositório da Conta para acessar dados do banco:
@Repository
public interface RegistrosRepositorio extends JpaRepository<Registros, Long>{

	@Query("SELECT r FROM Registros r WHERE r.atividade LIKE %?1%"
    		+ "OR r.data_atividade LIKE %?1% "
    		+ "OR r.usuario.nome LIKE %?1%"
    		+ "OR r.usuario.sobrenome LIKE %?1%"
    		+ "ORDER BY r.data_atividade DESC")
	List<Registros> pesquisarRegistro(String palavraChave);
	
	@Query("SELECT r FROM Registros r ORDER BY r.data_atividade DESC")
	List<Registros> todosRegistros();

}
