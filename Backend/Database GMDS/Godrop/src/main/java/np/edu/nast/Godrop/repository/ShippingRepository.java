package np.edu.nast.Godrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import np.edu.nast.Godrop.entity.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

}
