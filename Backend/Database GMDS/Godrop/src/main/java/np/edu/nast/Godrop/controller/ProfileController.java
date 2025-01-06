package np.edu.nast.Godrop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import np.edu.nast.Godrop.entity.Profile;
import np.edu.nast.Godrop.repository.ProfileRepository;
import np.edu.nast.Godrop.service.ProfileService;

@RestController
@RequestMapping("/profileApi")
public class ProfileController {

    private final ProfileRepository proRepo;
    private final ProfileService ProfileService;

    @Autowired
    public ProfileController(ProfileRepository proRepo, ProfileService ProfileService) {
        this.proRepo = proRepo;
        this.ProfileService = ProfileService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<Profile> login(@RequestBody Profile profile) {
        Profile authenticatedProfile = ProfileService.authenticate(profile.getEmail(), profile.getPassword());
        if (authenticatedProfile != null) {
            return new ResponseEntity<>(authenticatedProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/find")
    public List<Profile> getAllProfiles() {
        return proRepo.findAll();
    }

    @PostMapping("/create")
    public Profile createProfile(@RequestBody Profile profile) {
        System.out.println(profile);
        return proRepo.save(profile);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String contact = request.get("contact");
        String newPassword = request.get("newPassword");

        Profile profile = ProfileService.findByEmailAndContact(email, contact);
        if (profile != null) {
            profile.setPassword(newPassword);
            ProfileService.saveProfile(profile);
            return ResponseEntity.ok(Map.of("message", "Password reset successful"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid email or contact"));
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Profile> findUser(@PathVariable("id") Long id) {
        return proRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile, @PathVariable("id") Long id) {
        if (profile != null) {
            Profile existingProfile = proRepo.findById(id).orElse(null);
            if (existingProfile != null) {
                existingProfile.setFirstName(profile.getFirstName());
                existingProfile.setMiddleName(profile.getMiddleName());
                existingProfile.setLastName(profile.getLastName());
                existingProfile.setAddress(profile.getAddress());
                existingProfile.setEmail(profile.getEmail());
                existingProfile.setContact(profile.getContact());
                existingProfile.setPassword(profile.getPassword());
                return ResponseEntity.ok(proRepo.save(existingProfile));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("id") Long id) {
        proRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
