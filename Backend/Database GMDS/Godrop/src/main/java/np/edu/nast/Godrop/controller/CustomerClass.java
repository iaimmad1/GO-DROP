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

import np.edu.nast.Godrop.entity.Customer;
import np.edu.nast.Godrop.repository.CustomerRepository;


@RestController
@RequestMapping("/customerApi")
public class CustomerClass {

	@Autowired
	private CustomerRepository custRepo;
	
//findALL
	@GetMapping("/find")
	public List<Customer> getAllCustomers(){
		return custRepo.findAll();
	}

// create
	@PostMapping("/create")
	public Customer createCustomer(@RequestBody Customer customer){
		System.out.println(customer);
		
		return custRepo.save(customer);
	}
	
//Read
	@GetMapping("/read/{id}")
	public ResponseEntity<Customer> findUser(@PathVariable("id") Long id){
	    return custRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
//Update	
	@PutMapping("/update/{id}")
	public Customer updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id){
		if(customer!=null) {
			return custRepo.save(customer);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteCustomer(@PathVariable("id") Long id){
		custRepo.deleteById(id);
	}
}