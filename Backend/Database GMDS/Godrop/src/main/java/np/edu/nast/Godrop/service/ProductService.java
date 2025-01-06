package np.edu.nast.Godrop.service;

import np.edu.nast.Godrop.entity.Product;
import np.edu.nast.Godrop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}