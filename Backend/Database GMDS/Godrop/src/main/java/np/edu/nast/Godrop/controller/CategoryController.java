package np.edu.nast.Godrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import np.edu.nast.Godrop.entity.Category;
import np.edu.nast.Godrop.repository.CategoryRepository;
import np.edu.nast.Godrop.service.CategoryService;;

@RestController
@RequestMapping("/categoryApi")
public class CategoryController {

	@Autowired
	private CategoryRepository catRepo;
	private final CategoryService cs;
	public CategoryController(CategoryService cs) {
        this.cs = cs;
    }
	
//findALL
	@GetMapping("/find/{categoryName}")
    public ResponseEntity<Long> getCategoryIDByName(@PathVariable String categoryName) {
        Long categoryId = cs.getCategoryIDByName(categoryName);
        if (categoryId != null) {
        	  System.out.println("All categories id fetched");
            return new ResponseEntity<>(categoryId, HttpStatus.OK);
        } else {
        	System.out.println("error categories id fetching");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
      
	 }
	
	@GetMapping("/find")
	public List<Category> getAllCategorys(){
		System.out.println("All categories");
		return catRepo.findAll();
	}

// create
	@PostMapping("/create")
	public Category createCategory(@RequestBody Category Category){
		return catRepo.save(Category);
	}
	
	// Read
	@GetMapping("/read/{id}")
	public ResponseEntity<Category> findUser(@PathVariable("id") Long id){
	    return catRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
//Update	
	@PutMapping("/update/{id}")
	public Category updateCategory(@RequestBody Category Category, @PathVariable("id") Long id){
		if(Category!=null) {
			return catRepo.save(Category);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteCategory(@PathVariable("id") Long id){
		catRepo.deleteById(id);
	}
}