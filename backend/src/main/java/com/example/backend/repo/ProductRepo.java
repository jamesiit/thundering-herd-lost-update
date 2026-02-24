package com.example.backend.repo;

import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Transactional
    @Modifying
    @Query("update Product p set p.prodQuantity = p.prodQuantity - :qty where p.prodId = :id and p.prodQuantity > 0")
    int decrementProduct(@Param("qty") int clientQuantity, @Param("id") long prodId);
}
