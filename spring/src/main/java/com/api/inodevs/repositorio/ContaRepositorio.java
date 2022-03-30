package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Conta;

@Repository
public interface ContaRepositorio extends JpaRepository<Conta, Long>{

}
