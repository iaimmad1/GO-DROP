package np.edu.nast.Godrop.service;

import np.edu.nast.Godrop.entity.Profile;
import np.edu.nast.Godrop.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile authenticate(String email, String password) {
        return profileRepository.findByEmailAndPassword(email, password);
    }

    public Profile findByEmailAndContact(String email, String contact) {
        return profileRepository.findByEmailAndContact(email, contact);
    }

    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }
}
