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

import np.edu.nast.Godrop.entity.Cart;
import np.edu.nast.Godrop.repository.CartRepository;


@RestController
@RequestMapping("/cartApi")
public class CartController {

	@Autowired
	private CartRepository cartRepo;
	
//findALL
	@GetMapping("/find/{userId}")
	public List<Cart> getAllCarts(@PathVariable("userId") String userId){
		return cartRepo.findAllManually(userId);
	}

// create
	@PostMapping("/create")
	public Cart createCart(@RequestBody Cart cart){
		System.out.println(cart);
		
		return cartRepo.save(cart);
	}
	
//Read
	@GetMapping("/read/{id}")
	public ResponseEntity<Cart> findUser(@PathVariable("id") Long id){
	    return cartRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
//Update	
	@PostMapping("/update/{id}/{user_id}")
	public String updateCart( @PathVariable("id") Long id,@PathVariable("user_id") Long userId){
		cartRepo.updateProfileId(userId, id);
		return "success";
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteCart(@PathVariable("id") Long id){
		cartRepo.deleteById(id);
	}
}