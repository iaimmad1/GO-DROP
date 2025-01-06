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
import np.edu.nast.Godrop.entity.Shipping;
import np.edu.nast.Godrop.repository.ShippingRepository;



@RestController
@RequestMapping("/shippingApi")
public class ShippingController {

	@Autowired
	private ShippingRepository shRepo;
	
//findALL
	@GetMapping("/find")
	public List<Shipping> getAllShippings(){
		return shRepo.findAll();
	}

// create
	@PostMapping("/create")
	public Shipping createShipping(@RequestBody Shipping Shipping){
		System.out.println(Shipping);
		
		return shRepo.save(Shipping);
	}
	
//Read
	@GetMapping("/read/{id}")
	public ResponseEntity<Shipping> findUser(@PathVariable("id") Long id){
	    return shRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
//Update	
	@PutMapping("/update/{id}")
	public Shipping updateShipping(@RequestBody Shipping Shipping, @PathVariable("id") Long id){
		if(Shipping!=null) {
			return shRepo.save(Shipping);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteShipping(@PathVariable("id") Long id){
		shRepo.deleteById(id);
	}
}