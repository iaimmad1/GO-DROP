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

import np.edu.nast.Godrop.entity.orderInfo;
import np.edu.nast.Godrop.repository.orderInfoRepository;


@RestController
@RequestMapping("/orderInfoApi")
public class orderInfoController {

	@Autowired
	private orderInfoRepository infoRepo;
	
//findALL
	@GetMapping("/find")
	public List<orderInfo> getAllorderInfos(){
		return infoRepo.findAll();
	}

// create
	@PostMapping("/create")
	public orderInfo createorderInfo(@RequestBody orderInfo info){
		System.out.println(info);
		
		return infoRepo.save(info);
	}
	
//Read
	@GetMapping("/read/{id}")
	public ResponseEntity<orderInfo> findorderInfo(@PathVariable("id") Long id){
	    return infoRepo.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
//Update	
	@PutMapping("/update/{id}")
	public orderInfo updateorderInfo(@RequestBody orderInfo info, @PathVariable("id") Long id){
		if(info!=null) {
			return infoRepo.save(info);
		}
		return null;
	}
	
//delete
	@DeleteMapping("/delete/{id}")
	public void deleteorderInfo(@PathVariable("id") Long id){
		infoRepo.deleteById(id);
	}
}