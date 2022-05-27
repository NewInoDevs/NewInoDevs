package com.api.inodevs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.inodevs.entidades.Notificacoes;

@Repository
public interface NotificacoesRepositorio extends JpaRepository<Notificacoes, Long> {
    @Query("SELECT COUNT(u) FROM Notificacoes u WHERE u.tipo=?1 AND destinatario=?2")
    long contar(String tipo, String destinatario);
}