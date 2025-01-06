package np.edu.nast.Godrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import np.edu.nast.Godrop.entity.Order;
import np.edu.nast.Godrop.repository.OrderRepository;


@RestController
@RequestMapping("/orderApi")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;
	
//findALL
	@GetMapping("/find")
	public List<Order> getAllOrders(){
		return orderRepo.findAll();
	}

// create
	@PostMapping("/create")
	public Order createOrder(@RequestBody Order Order){
		System.out.println(Order);
		
		return orderRepo.save(Order);
	}
	
//Read
	@GetMapping("/read/{id}")
	public ResponseEntity<Order> findUser(@PathVariable("id") Long id){
	    return orderRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
	
//Update	
	@PutMapping("/update/{id}")
	public Order updateOrder(@RequestBody Order Order, @PathVariable("id") Long id){
		if(Order!=null) {
			return orderRepo.save(Order);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteOrder(@PathVariable("id") Long id){
		orderRepo.deleteById(id);
	}
}