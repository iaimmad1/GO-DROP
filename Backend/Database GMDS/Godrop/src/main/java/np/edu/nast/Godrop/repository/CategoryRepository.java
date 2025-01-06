package np.edu.nast.Godrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import np.edu.nast.Godrop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
	 @Query("SELECT c.id FROM Category c WHERE c.categoryName = :categoryName")
	    Long findCategoryIdByName(@Param("categoryName") String categoryName);

	
	
}