package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.Product;
import cz.sda.java.remotesk1.invoices.repository.ProductRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
class ProductServiceBean implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    ProductServiceBean(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(@NonNull String name, @NonNull BigDecimal price) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        var product = new Product(UUID.randomUUID().toString(), name, price);
        productRepository.save(product);
        log.info("Product {} added", product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), true).toList();
    }

    @Override
    public Product getProduct(String id) {
        log.trace("Getting product with id {}", id);
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product with id " + id + " not found")
        );
    }

    @Override
    public void removeProduct(String id) {
        var product = getProduct(id);
        productRepository.delete(product);
        log.info("Product {} removed", product);
    }

    @Override
    public Product updateProduct(String id, String name, BigDecimal price) {
        var product = getProduct(id);
        if (StringUtils.hasText(name)) {
            product.setName(name);
        }
        if (price != null && price.compareTo(BigDecimal.ZERO) >= 0) {
            product.setPrice(price);
        }
        productRepository.save(product);
        log.info("Product {} updated", product);
        return product;
    }
}
