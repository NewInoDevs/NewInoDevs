package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Unidade;

//Repositório da Unidade para acessar dados do banco:
@Repository
public interface UnidadeRepositorio extends JpaRepository<Unidade, Long>{
}
