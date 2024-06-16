package com.onlineShop.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineShop.store.entities.Comment;
import com.onlineShop.store.entities.CommentStatus;

import jakarta.transaction.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
	public List<Comment> findByUser_Id(long userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Comment c WHERE c.user.email = :email AND c.product.id = :productId")
    boolean existsByUserEmailAndProductId(@Param("email") String email, @Param("productId") long productId);
    
    public List<Comment> findByStatus(CommentStatus status);
    
    @Query("SELECT c FROM Comment c WHERE c.product.id = :productId")
    public List<Comment> findByProductId(@Param("productId") long productId);
    
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.product.id = :productId")
    void deleteAllCommentsByProductId(@Param("productId") long productId);

}
