package com.crudFrontend.crud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crudFrontend.crud.Model.ItemCarrinho;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    
}
