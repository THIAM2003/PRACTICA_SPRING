package com.dailycodework.dreamshops.service.product;

import java.util.List;

import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;

public interface IProductService {

    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);
    
    List<Product> getProductsByBrandAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);

    ProductDto convertToDto(Product product);

    List<ProductDto> getConvertedProducts(List<Product> products);

}
