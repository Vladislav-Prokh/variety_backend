package com.onlineShop.store.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlineShop.store.dto.OrderInfoDTO;
import com.onlineShop.store.dto.OrderRequestDTO;
import com.onlineShop.store.dto.OrderStatistics;
import com.onlineShop.store.entities.Order;
import com.onlineShop.store.entities.OrderStatus;
import com.onlineShop.store.entities.OrderedProduct;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.repositories.OrderRepository;
import com.onlineShop.store.repositories.OrderedProductRepository;
import com.onlineShop.store.repositories.ProductRepository;
import com.onlineShop.store.repositories.UserRepository;

@Service
public class OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private OrderedProductRepository orderedProductRepository;

    public List<OrderedProduct> findOrderedProductsByOrderId(Long orderId) {
        return orderedProductRepository.findAllByOrderId(orderId);
    }
	
	public Order saveOrder(OrderRequestDTO orderRequest) {
		OrderInfoDTO orderInfo = orderRequest.getOrderInfo();
		User user =null;
		
		if(orderRequest.getOrderInfo().getUserId()!=null) {
			Long userId = (long)orderRequest.getOrderInfo().getUserId();
			user = userRepository.findById(userId).get();
		}
		
		Order order = new Order(null, orderInfo.getCustomerName(),orderInfo.getCustomerSecondName(),orderInfo.getCustomerLastName(),orderInfo.getCityToDeliver(),orderInfo.getPostOfficeToDeliver(),null, orderInfo.getStatus(),LocalDateTime.now(),orderInfo.getTotalCost(),user);
		
		List<Integer> quantitiesPerProduct = orderRequest.getProductAmountInOrderByIds();
		
		List<OrderedProduct> orderedProducts = IntStream.range(0, orderRequest.getProductIds().size())
			    .mapToObj(index -> {
			        Long productId = orderRequest.getProductIds().get(index);
			        Product product = productRepository.findById(productId)
			                                            .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
			        Integer quantity = quantitiesPerProduct.get(index);
			        return new OrderedProduct(null, order, product, quantity);
			    })
			    .collect(Collectors.toList());

			order.setOrderedProducts(orderedProducts);
	
		
		return orderRepository.save(order);
		
	}
	
	public List<Order> findAllByUser(Long user_id){
		User user = userRepository.findById(user_id).get();
		return orderRepository.findByUser(user);
	}
	
	public void deleteOrderById(Long id) {
		 orderRepository.deleteById(id);
	}
	public void changeOrderStatus(Long id, OrderStatus newStatus) {
		Order orderToChangeSatus = findById(id);
		orderToChangeSatus.setStatus(newStatus);
		orderRepository.save(orderToChangeSatus);
	}
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}
	public List<Order> findAll(){
		return orderRepository.findAll();
	}
	
	public List<Order> findAllOrdersByStatus(OrderStatus status){
		return orderRepository.findByStatus(status);
	}

	public List<Order> findByAnyMatch(String param) {
		return orderRepository.findAllByFields(param);
	}
	
	public List<OrderStatistics> countOrderByDateRangeAndStatus(String startDateStr, String endDateStr, OrderStatus status) {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	        LocalDateTime startDate = LocalDateTime.parse(startDateStr + " 00:00:00.000000", formatter);
	        LocalDateTime endDate = LocalDateTime.parse(endDateStr + " 23:59:59.999999", formatter);
	        System.out.println(startDate);
	        System.out.println(endDate);
	        return  orderRepository.findOrderStatisticsByDateRange(startDate, endDate, status);
	}

	public List<OrderStatistics> countOrderByDateAndStatus(String startDateStr, OrderStatus status) {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr + " 00:00:00.000000", formatter);
	        LocalDateTime endDateTime = LocalDateTime.parse(startDateStr + " 23:59:59.999999", formatter);
		
		return  orderRepository.findOrderStatisticsByDate(startDateTime,endDateTime, status);
	}

}
