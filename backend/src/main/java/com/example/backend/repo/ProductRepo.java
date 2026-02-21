package com.example.backend.repo;

import com.example.backend.model.Product;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, Long> {

    //specify lock type
    @Lock(LockModeType.PESSIMISTIC_WRITE)

    // set the timeout. MySQL ignores this value but keeping it for good practice regardless
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})

    //query logic
    @Query("select p from Product p where p.prodId = :id")
    Product findByIdAndLock(@Param("id") long id);

}
