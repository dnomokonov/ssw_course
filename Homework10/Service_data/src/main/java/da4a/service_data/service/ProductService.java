package da4a.service_data.service;

import da4a.service_data.model.Product;
import da4a.service_data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}