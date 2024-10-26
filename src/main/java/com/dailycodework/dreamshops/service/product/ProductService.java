package com.dailycodework.dreamshops.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.exceptions.ProductNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CategoryRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Constructor inyection
//@RequiredArgsConstructor genera un constructor que incluye todos los campos final y los campos marcados
// como @NonNull en una clase(puede cumplir una función similar si se utiliza con la inyección de dependencias
// por constructor en Spring.)En Spring, cuando tienes un constructor con dependencias, no necesitas explícitamente
// la anotación @Autowired en el constructor. Spring es capaz de detectar automáticamente el constructor y utilizarlo
// para inyectar las dependencias necesarias. Si se marcan las dependencias con final Spring las inyectará automaticamente
//La anotación @Override le indica al compilador que el método anotado debe sobrescribir
//un método con la misma firma en una superclase o interfaz. 

public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as a new product category
        // If No, then save it as a new category
        // Then set as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
            .orElseGet(()-> {
                Category newCategory = new Category(null, request.getCategory().getName(), null);
                return categoryRepository.save(newCategory);
            });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).
            ifPresentOrElse(productRepository::delete,
            ()->{throw new ProductNotFoundException("Product not found-prueba git!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
            .map(existingProduct -> updateExistingProduct(existingProduct, request))
            .map(productRepository :: save)
            .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName()); 
        existingProduct.setCategory(category);
        return existingProduct;
        
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

}
