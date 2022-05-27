package com.api.inodevs.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Conta;

// Repositório da Conta para acessar dados do banco:
@Repository
public interface ContaRepositorio extends JpaRepository<Conta, Long>{
    @Query("SELECT c FROM Conta c WHERE c.status LIKE %?1% "
    		+ "OR c.contrato.unidade.nome LIKE %?1% "
    		+ "OR c.contrato.concessionaria.tipo_conta LIKE %?1%"
    		+ "OR c.contrato.codigo LIKE %?1%"
    		+ "OR c.codi LIKE %?1%"
    		+ "OR c.consumo LIKE %?1%"
    		+ "OR c.media_consumo LIKE %?1%")
    public List<Conta> pesquisarConta(String palavraChave);
    
    @Query("SELECT c FROM Conta c WHERE c.contrato.codigo = ?1")
    public List<Conta> contasContrato(Long codigoContrato);
}
