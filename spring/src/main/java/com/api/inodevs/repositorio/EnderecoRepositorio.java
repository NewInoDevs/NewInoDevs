package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.inodevs.entidades.Endereco;

//Repositório do Endereço para acessar dados do banco:
public interface EnderecoRepositorio extends JpaRepository<Endereco, Long>  {
}