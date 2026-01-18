package com.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventory.model.Product;
import com.inventory.model.StockLog;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.StockLogRepository;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final StockLogRepository logRepo;

    public ProductService(ProductRepository productRepo, StockLogRepository logRepo) {
        this.productRepo = productRepo;
        this.logRepo = logRepo;
    }

    // ADD PRODUCT
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    // EDIT PRODUCT
    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    // DELETE PRODUCT
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    // STOCK IN
    public void stockIn(String sku, int qty, String role) {
        Product p = productRepo.findBySku(sku).orElseThrow();
        p.setQuantity(p.getQuantity() + qty);
        productRepo.save(p);

        log("STOCK_IN", sku, qty, role);
    }

    // STOCK OUT
    public void stockOut(String sku, int qty, String role) {
        Product p = productRepo.findBySku(sku).orElseThrow();

        if (p.getQuantity() < qty) {
            throw new RuntimeException("Insufficient stock");
        }

        p.setQuantity(p.getQuantity() - qty);
        productRepo.save(p);

        log("STOCK_OUT", sku, qty, role);
    }

    public List<Product> allProducts() {
        return productRepo.findAll();
    }

    private void log(String action, String sku, int qty, String role) {
        StockLog log = new StockLog();
        log.setAction(action);
        log.setSku(sku);
        log.setQuantity(qty);
        log.setPerformedBy(role);
        logRepo.save(log);
    }
}
