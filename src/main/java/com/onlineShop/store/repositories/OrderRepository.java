package com.onlineShop.store.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineShop.store.dto.OrderStatistics;
import com.onlineShop.store.entities.Order;
import com.onlineShop.store.entities.OrderStatus;
import com.onlineShop.store.entities.User;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long>{
	public List<Order> findByStatus(OrderStatus status);
	
	@Query("SELECT o FROM Order o WHERE " +
	        "LOWER(o.customerName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	        "LOWER(o.customerSecondName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	        "LOWER(o.customerLastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	        "LOWER(o.cityToDeliver) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	        "LOWER(o.postOfficeToDeliver) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	        "CAST(o.id AS string) LIKE :searchTerm")
	List<Order> findAllByFields(@Param("searchTerm") String searchTerm);
	
	
	

	@Query("SELECT new com.onlineShop.store.dto.OrderStatistics(DATE(o.date), COUNT(o), SUM(o.totalCost)) " +
		       "FROM Order o " +
		       "WHERE o.date >= :startDate AND o.date <= :endDate " +
		       "AND o.status = :status " +
		       "GROUP BY DATE(o.date)")
		List<OrderStatistics> findOrderStatisticsByDateRange(@Param("startDate") LocalDateTime startDate,
		                                          @Param("endDate") LocalDateTime endDate,
		                                          @Param("status") OrderStatus status);

	
	@Query("SELECT new com.onlineShop.store.dto.OrderStatistics(DATE(o.date), COUNT(o), SUM(o.totalCost)) " +
		       "FROM Order o " +
		       "WHERE o.date >= :startDate AND o.date <= :endDate " +
		       "AND o.status = :status " +
		       "GROUP BY DATE(o.date)")
		public List<OrderStatistics> findOrderStatisticsByDate(@Param("startDate") LocalDateTime startDayTime, @Param("endDate") LocalDateTime endDayTime, @Param("status") OrderStatus status);

 
	public List<Order> findByUser(User user);

}

