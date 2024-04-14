package com.DogFoot.adpotAnimal.products.service;

import com.DogFoot.adpotAnimal.products.entity.Product;
import com.DogFoot.adpotAnimal.products.dto.ProductDto;
import com.DogFoot.adpotAnimal.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductPrice(productDto.getProductPrice());
        product.setProductName(productDto.getProductName());
        product.setProductStock(productDto.getProductStock());
        product.setProductLike(productDto.getProductLike());

        // 카테고리 정보가 있다면 설정
        return productRepository.save(product);
    }

    // 상품 리스트
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        productRepository.delete(existingProduct);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    public Product updateProduct(Long id, ProductDto updatedProductDto) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        existingProduct.setProductPrice(updatedProductDto.getProductPrice());
        existingProduct.setProductName(updatedProductDto.getProductName());
        existingProduct.setProductStock(updatedProductDto.getProductStock());
        existingProduct.setProductLike(updatedProductDto.getProductLike());

        return productRepository.save(existingProduct);
    }

}
