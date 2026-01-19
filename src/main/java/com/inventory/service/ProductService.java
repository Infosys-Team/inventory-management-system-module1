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
    private final AlertService alertService;   // ✅ ADD THIS

    // ✅ UPDATE CONSTRUCTOR
    public ProductService(
            ProductRepository productRepo,
            StockLogRepository logRepo,
            AlertService alertService
    ) {
        this.productRepo = productRepo;
        this.logRepo = logRepo;
        this.alertService = alertService;
    }

    // ================= ADD PRODUCT =================
    public Product addProduct(Product product) {

        Product saved = productRepo.save(product);

        checkAlert(saved);   // ✅ alert check

        return saved;
    }

    // ================= EDIT PRODUCT =================
    public Product updateProduct(Product product) {

        Product updated = productRepo.save(product);

        checkAlert(updated); // ✅ alert check

        return updated;
    }

    // ================= DELETE PRODUCT =================
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    // ================= STOCK IN =================
    public void stockIn(String sku, int qty, String role) {

        Product p = productRepo.findBySku(sku).orElseThrow();

        p.setQuantity(p.getQuantity() + qty);
        productRepo.save(p);

        log("STOCK_IN", sku, qty, role);

        checkAlert(p);   // ✅ alert check
    }

    // ================= STOCK OUT =================
    public void stockOut(String sku, int qty, String role) {

        Product p = productRepo.findBySku(sku).orElseThrow();

        if (p.getQuantity() < qty) {
            throw new RuntimeException("Insufficient stock");
        }

        p.setQuantity(p.getQuantity() - qty);
        productRepo.save(p);

        log("STOCK_OUT", sku, qty, role);

        checkAlert(p);   // ✅ alert check
    }

    // ================= GET ALL =================
    public List<Product> allProducts() {
        return productRepo.findAll();
    }

    // ================= STOCK LOG =================
    private void log(String action, String sku, int qty, String role) {

        StockLog log = new StockLog();
        log.setAction(action);
        log.setSku(sku);
        log.setQuantity(qty);
        log.setPerformedBy(role);

        logRepo.save(log);
    }

    // ================= ALERT LOGIC =================
    private void checkAlert(Product p) {

        if (p.getQuantity() == 0) {
            alertService.createAlert(
                "CRITICAL",
                p.getName() + " is out of stock"
            );
        }
        else if (p.getQuantity() <= 5) {
            alertService.createAlert(
                "LOW",
                p.getName() + " stock is low"
            );
        }
    }
}
