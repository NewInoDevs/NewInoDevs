package com.api.inodevs.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Unidade;

//Repositório da Unidade para acessar dados do banco:
@Repository
public interface UnidadeRepositorio extends JpaRepository<Unidade, Long>{
    @Query("SELECT u FROM Unidade u WHERE u.nome LIKE %?1%"
    		+ "OR u.cnpj LIKE %?1% "
    		+ "OR u.telefone LIKE %?1%"
    		+ "OR u.endereco.cep LIKE %?1%"
    		+ "OR u.status LIKE %?1%")
    public List<Unidade> pesquisarUnidade(String palavraChave);
    
    @Query("SELECT u FROM Unidade u WHERE u.nome LIKE %?1%")
    public List<Unidade> pesquisarRelatorio(String palavraChave);
}
