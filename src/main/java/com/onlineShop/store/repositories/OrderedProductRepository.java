package com.onlineShop.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onlineShop.store.entities.OrderedProduct;

@Repository
public interface OrderedProductRepository  extends JpaRepository<OrderedProduct,Long>{

    List<OrderedProduct> findAllByOrderId(Long orderId);
    
    @Modifying
    @Query("delete from OrderedProduct where product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}