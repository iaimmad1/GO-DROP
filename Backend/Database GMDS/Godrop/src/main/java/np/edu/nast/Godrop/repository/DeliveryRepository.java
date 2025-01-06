package np.edu.nast.Godrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import np.edu.nast.Godrop.entity.Delivery_Person;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery_Person, Long>{

}
