package cz.sda.java.remotesk1.invoices.controller.web;

import cz.sda.java.remotesk1.invoices.controller.web.request.CreateProduct;
import cz.sda.java.remotesk1.invoices.controller.web.request.UpdateProduct;
import cz.sda.java.remotesk1.invoices.model.Product;
import cz.sda.java.remotesk1.invoices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
class ProductController {

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        return "product-list";
    }

    @GetMapping("/edit/{id}")
    public String getProductById(@PathVariable("id") String id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "product-edit";
    }

    @PostMapping("/add")
    public String addProduct(CreateProduct product) {
        productService.addProduct(product.name(), product.price());
        return "redirect:/products/";
    }

    @PostMapping("/update")
    public String updateProduct(UpdateProduct product, Model model) {
        productService.updateProduct(product.id(), product.name(), product.price());
        return "redirect:/products/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id, Model model) {
        productService.removeProduct(id);
        return "redirect:/products/";
    }
}
