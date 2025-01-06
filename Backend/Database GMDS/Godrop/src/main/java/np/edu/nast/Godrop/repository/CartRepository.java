package np.edu.nast.Godrop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import np.edu.nast.Godrop.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	@Modifying
	@Transactional
	@Query(value = "update tbl_carts set customer_id = ?1 where id = ?2",nativeQuery = true)
	public void updateProfileId(Long userId, Long cardId);
	@Query(value = "select * from tbl_carts where customer_id = ?1",nativeQuery = true)
	public List<Cart> findAllManually(String user_id);
}


