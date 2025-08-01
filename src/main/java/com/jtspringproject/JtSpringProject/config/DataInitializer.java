package com.jtspringproject.JtSpringProject.config;

import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.repositories.CategoryRepository;
import com.jtspringproject.JtSpringProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DataInitializer(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty
        if (productRepository.count() == 0) {
            // Create Categories
            Category weapons = new Category();
            weapons.setName("Weapons");
            categoryRepository.save(weapons);

            Category armor = new Category();
            armor.setName("Armor");
            categoryRepository.save(armor);
            
            Category artifacts = new Category();
            artifacts.setName("Artifacts");
            categoryRepository.save(artifacts);

            // Create Products with local image paths
//            createProduct("Mjolnir", "The legendary hammer of Thor, capable of leveling mountains.", 9999, "/images/mjolnir.jpg", weapons);
//            createProduct("Gungnir", "Odin's spear, which is said to never miss its target.", 8500, "/images/gungnir.jpg", weapons);
//            createProduct("Valkyrie Helm", "A winged helm worn by the choosers of the slain.", 1200, "/images/valkyrie-helm.jpg", armor);
//            createProduct("Draupnir", "A golden ring that magically creates eight new rings every ninth night.", 7500, "/images/draupnir.jpg", artifacts);
//            createProduct("Hofund", "The enchanted sword of the guardian Heimdall.", 4500, "/images/hofund.jpg", weapons);
//            createProduct("Aesir Weave Tunic", "Lightweight but stronger than steel, woven by the gods.", 2500, "/images/aesir-weave-tunic.jpg", armor);
        }
    }

    private void createProduct(String name, String description, int price, String imageUrl, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(imageUrl);
        product.setCategory(category);
        product.setQuantity(10); // Default quantity
        product.setWeight(15);   // Default weight
        productRepository.save(product);
    }
}
