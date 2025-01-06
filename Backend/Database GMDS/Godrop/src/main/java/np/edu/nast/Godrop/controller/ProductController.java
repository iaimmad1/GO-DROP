package np.edu.nast.Godrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import np.edu.nast.Godrop.entity.Product;
import np.edu.nast.Godrop.entity.Seller;
import np.edu.nast.Godrop.repository.ProductRepository;
import np.edu.nast.Godrop.service.ProductService;



@RestController
@RequestMapping("/productApi")
public class ProductController {

	@Autowired
	private ProductRepository proRepo;
	private ProductService productService;
	
	
	
	
//findALL
	@GetMapping("/find")
	public List<Product> getAllProducts(){
		return proRepo.findAll();
	}
		// create
		@PostMapping("/create")
		public Product createProduct(@RequestBody Product product){
			return proRepo.save(product);
		}	
//Read
	@GetMapping("/find/{id}")
	public ResponseEntity<Product> findproduct(@PathVariable("id") Long id){
	    return proRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
	
	//Update	
		@PutMapping("/update/{id}")
		public Product updateProduct(@RequestBody Product product, @PathVariable("id") Long id){
			if(product!=null) {
				return proRepo.save(product);
			}
			return null;
		}
	
//Update	
	@GetMapping("/read/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable("id") Long id) {
		System.out.println("1");
        return proRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteProduct(@PathVariable("id") Long id){
		proRepo.deleteById(id);
	}
	
	@GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("name") String name) {
        return productService.searchProductsByName(name);
    }
//	@GetMapping("/search")
//	public List<Product> searchProductsByName(@RequestParam String name) {
//	    System.out.println("Search query: " + name);
//	    List<Product> products = productService.searchProductsByName(name);
//	    System.out.println("Found products: " + products.size());
//	    return products;
//	}

	  
}