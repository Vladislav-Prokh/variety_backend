package com.onlineShop.store.contollers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.onlineShop.store.dto.OrderRequestDTO;
import com.onlineShop.store.dto.OrderStatistics;
import com.onlineShop.store.entities.Order;
import com.onlineShop.store.entities.OrderStatus;
import com.onlineShop.store.entities.OrderedProduct;
import com.onlineShop.store.services.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Long addOrder(@RequestBody OrderRequestDTO orderRequest) {
		return orderService.saveOrder(orderRequest).getId();
	}
	
	@RequestMapping(value = "/delete/{order_id}", method = RequestMethod.POST)
	public void removeOrderById(@PathVariable ("order_id")Integer order_id) {
		orderService.deleteOrderById((long)order_id);
	}
	
	@RequestMapping(value = "/change/status", method = RequestMethod.POST)
	public void changeStatusOrder(@RequestParam("order_id") Long orderId, @RequestParam("status") OrderStatus status) {
	    orderService.changeOrderStatus(orderId, status);
	}
	
	@RequestMapping(value = "/search/{searchParam}", method = RequestMethod.GET)
	public List<Order> findAllOrdersByParam(@PathVariable("searchParam") String param) {
		return orderService.findByAnyMatch(param);
	}
	
	
	@RequestMapping(value = "/All", method = RequestMethod.GET)
	public List<Order> findAllOrders() {
		return orderService.findAll();
	}
	
	@RequestMapping(value = "/All/{id}", method = RequestMethod.GET)
	public Order findOrderById(Long id) {
		return orderService.findById(id);
	}
	
	@RequestMapping(value = "/{order_id}/products", method = RequestMethod.GET)
	public List<OrderedProduct> findProductsByOrderById(@PathVariable ("order_id")Long order_id) {
		return orderService.findOrderedProductsByOrderId(order_id);
	}
	
	@RequestMapping(value = "/{status}", method = RequestMethod.GET)
	public List<Order> findOrdersByStatus(@PathVariable ("status") OrderStatus status) {
		return orderService.findAllOrdersByStatus(status);
	}
	@RequestMapping(value = "/countByDateAndStatus", method = RequestMethod.GET)
	public List<OrderStatistics> countOrderByDateRangeAndStatus(@RequestParam("startDate") String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam("status") OrderStatus status) {
	    if (endDate != null) {
	        return orderService.countOrderByDateRangeAndStatus(startDate, endDate, status);
	    } else {
	        return orderService.countOrderByDateAndStatus(startDate, status);
	    }
	}
}
