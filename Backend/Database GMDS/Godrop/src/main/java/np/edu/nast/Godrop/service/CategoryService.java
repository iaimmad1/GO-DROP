package np.edu.nast.Godrop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import np.edu.nast.Godrop.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    
    public Long getCategoryIDByName(String categoryName) {
        return categoryRepository.findCategoryIdByName(categoryName);
    }
}