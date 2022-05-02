package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Fatura;

//Repositório da Conta para acessar dados do banco:
@Repository
public interface FaturaRepositorio extends JpaRepository<Fatura, Long>{
}
