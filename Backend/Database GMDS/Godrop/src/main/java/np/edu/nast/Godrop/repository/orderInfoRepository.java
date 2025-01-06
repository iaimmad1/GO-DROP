package np.edu.nast.Godrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import np.edu.nast.Godrop.entity.orderInfo;

@Repository
public interface orderInfoRepository extends JpaRepository<orderInfo, Long>{

}
