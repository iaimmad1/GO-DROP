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

import np.edu.nast.Godrop.entity.Delivery_Person;
import np.edu.nast.Godrop.repository.DeliveryRepository;


@RestController
@RequestMapping("/deliveryApi")
public class DeliveryController {

	@Autowired
	private DeliveryRepository delRepo;
	
//findALL
	@GetMapping("/find")
	public List<Delivery_Person> getAllDelivery_Persons(){
		return delRepo.findAll();
	}

// create
	@PostMapping("/create")
	public Delivery_Person createDelivery_Person(@RequestBody Delivery_Person Delivery_Person){
		System.out.println(Delivery_Person);
		
		return delRepo.save(Delivery_Person);
	}
	
//Read
	@GetMapping("/read/{id}")
	public ResponseEntity<Delivery_Person> findUser(@PathVariable("id") Long id){
	    return delRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
//Update	
	@PutMapping("/update/{id}")
	public Delivery_Person updateDelivery_Person(@RequestBody Delivery_Person Delivery_Person, @PathVariable("id") Long id){
		if(Delivery_Person!=null) {
			return delRepo.save(Delivery_Person);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteDelivery_Person(@PathVariable("id") Long id){
		delRepo.deleteById(id);
	}
}