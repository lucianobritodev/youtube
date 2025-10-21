package com.lucianobrito.youtube.infra.repositories;

import com.lucianobrito.youtube.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Modifying
    @Query("UPDATE Produto p SET p.ativo = :status WHERE p.id = :id")
    void ativar(@Param("id") UUID id, @Param("status") boolean status);

}
