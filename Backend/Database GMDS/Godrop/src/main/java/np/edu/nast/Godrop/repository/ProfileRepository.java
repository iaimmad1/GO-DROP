package np.edu.nast.Godrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import np.edu.nast.Godrop.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByEmailAndPassword(String email, String password);

    Profile findByEmailAndContact(String email, String contact);
}
