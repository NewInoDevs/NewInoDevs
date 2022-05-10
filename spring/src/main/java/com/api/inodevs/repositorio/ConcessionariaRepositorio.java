package com.api.inodevs.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Concessionaria;

//Repositório da Concessionária para acessar dados do banco:
@Repository
public interface ConcessionariaRepositorio extends JpaRepository<Concessionaria, Long> {
    @Query("SELECT c FROM Concessionaria c WHERE c.nome LIKE %?1% "
    		+ "OR c.codigo LIKE %?1% "
    		+ "OR c.tipo_conta LIKE %?1%"
    		+ "OR c.status LIKE %?1%")
    public List<Concessionaria> pesquisarConcessionaria(String palavraChave);
}
