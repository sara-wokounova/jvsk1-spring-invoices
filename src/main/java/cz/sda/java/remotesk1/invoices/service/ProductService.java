package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.model.Product;
import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product addProduct(@NonNull String name, @NonNull @Min(0) BigDecimal price);

    List<Product> getAllProducts();

    Product getProduct(String id);

    void removeProduct(String id);

    Product updateProduct(String id, String name, BigDecimal price);
}
