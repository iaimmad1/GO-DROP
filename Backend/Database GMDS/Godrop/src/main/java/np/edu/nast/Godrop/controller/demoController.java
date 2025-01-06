package np.edu.nast.Godrop.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import np.edu.nast.Godrop.entity.demo;
import np.edu.nast.Godrop.repository.demoRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/demoApi")
public class demoController {

    @Autowired
    private demoRepository demoRepo;
    
    @Autowired
    private DataSource dataSource;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("image") String image) {
        if (image != null && !image.isEmpty()) {
            byte[] decodedImage = Base64.getDecoder().decode(image);

            // Save the decoded image to a file
            String filePath = "uploaded_image.jpg";
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(decodedImage);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("{\"message\": \"Error saving image to file\"}");
            }

            // Save the decoded image to the database
            demo demoEntity = new demo();
            demoEntity.setPhoto(decodedImage);
            demoRepo.save(demoEntity);

            return ResponseEntity.status(HttpStatus.OK)
                                 .body("{\"message\": \"Image uploaded successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("{\"message\": \"No image provided\"}");
        }
    }

    // findAll
    @GetMapping("/find")
    public List<demo> getAllDemos() {
        return demoRepo.findAll();
    }

    // create
    @PostMapping("/create")
    public demo createDemo(@RequestBody demo demoEntity) {
        System.out.println(demoEntity);
        return demoRepo.save(demoEntity);
    }

    // read
    @GetMapping("/read/{id}")
    public ResponseEntity<demo> findDemo(@PathVariable("id") Long id) {
        return demoRepo.findById(id)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // update
    @PutMapping("/update/{id}")
    public ResponseEntity<demo> updateDemo(@RequestBody demo demoEntity, @PathVariable("id") Long id) {
        return demoRepo.findById(id)
                       .map(existingDemo -> {
                           existingDemo.setPhoto(demoEntity.getPhoto());
                           demoRepo.save(existingDemo);
                           return ResponseEntity.ok(existingDemo);
                       })
                       .orElseGet(() -> {
                           demoEntity.setId(id);
                           demoRepo.save(demoEntity);
                           return ResponseEntity.ok(demoEntity);
                       });
    }

    // delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDemo(@PathVariable("id") Long id) {
        if (demoRepo.existsById(id)) {
            demoRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
