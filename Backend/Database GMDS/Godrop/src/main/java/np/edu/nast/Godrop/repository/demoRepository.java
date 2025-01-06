package np.edu.nast.Godrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import np.edu.nast.Godrop.entity.demo;
@Repository
public interface demoRepository extends JpaRepository<demo, Long>{

}
