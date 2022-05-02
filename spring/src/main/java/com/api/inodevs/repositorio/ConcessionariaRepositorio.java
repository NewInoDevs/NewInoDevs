package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Concessionaria;

//Repositório da Concessionária para acessar dados do banco:
@Repository
public interface ConcessionariaRepositorio extends JpaRepository<Concessionaria, Long> {
}
