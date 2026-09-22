package com.crudFrontend.crud.Repository.PixRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudFrontend.crud.Model.Pixmodels.Pedido;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByTransactionId(String transactionId);
}
