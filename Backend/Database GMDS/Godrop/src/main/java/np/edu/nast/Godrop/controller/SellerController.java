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
import np.edu.nast.Godrop.entity.Product;
import np.edu.nast.Godrop.entity.Seller;
import np.edu.nast.Godrop.repository.CartRepository;
import np.edu.nast.Godrop.repository.SellerRepository;


@RestController
@RequestMapping("/sellerApi")
public class SellerController {

	@Autowired
	private SellerRepository cartRepo;
	
//findALL
	@GetMapping("/find")
	public List<Seller> getAllSeller(){
		return cartRepo.findAll();
	}

// create
	@PostMapping("/create")
	public Seller createSeller(@RequestBody Seller cart){
		System.out.println(cart);
		
		return cartRepo.save(cart);
	}
	
//Read
	//Read
		@GetMapping("/find/{id}")
		public ResponseEntity<Seller> findproduct(@PathVariable("id") Long id){
		    return cartRepo.findById(id)
		            .map(ResponseEntity::ok)
		            .orElse(ResponseEntity.notFound().build());
		}
//Update	
	@PutMapping("/update/{id}")
	public Seller updateSeller(@RequestBody Seller cart, @PathVariable("id") Long id){
		if(cart!=null) {
			return cartRepo.save(cart);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteSeller(@PathVariable("id") Long id){
		cartRepo.deleteById(id);
	}
}