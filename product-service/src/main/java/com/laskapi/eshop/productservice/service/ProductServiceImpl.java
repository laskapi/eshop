package com.laskapi.eshop.productservice.service;

import com.laskapi.eshop.productservice.dto.ProductDto;
import com.laskapi.eshop.productservice.entity.Category;
import com.laskapi.eshop.productservice.entity.Product;
import com.laskapi.eshop.productservice.exception.CustomServiceException;
import com.laskapi.eshop.productservice.repository.CategoryRespository;
import com.laskapi.eshop.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRespository categoryRepository;

    @Override
    public Product addProduct(ProductDto productDto) {
        Category category =
                categoryRepository.findById(productDto.getCategory_id()).orElseThrow(() -> new CustomServiceException(
                        "No such category", HttpStatus.NOT_FOUND));

        Product product=productRepository.save( Product.builder()
                .name(productDto.getName())
                .category(category)
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build());
        log.info("Product %s added".formatted(product.getName()));

        return product;
    }

    @Override
    public ProductDto getProductById(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomServiceException("No such Element", HttpStatus.NOT_FOUND));
        log.info("Product %s acquired".formatted(product.getName()));
        return new ProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDto::new).toList();

    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        Category cat = categoryRepository.findByName(category);
        return productRepository.findAllByCategory(cat).stream().map(ProductDto::new).toList();
    }


    @Override
    public Product updateProduct(long id, ProductDto productDto) {
        Category cat= categoryRepository.findById(productDto.getCategory_id()).orElseThrow(
                ()->new CustomServiceException("No such category",HttpStatus.NOT_FOUND));

       return productRepository.findById(id).map(product->
       {
           product.setName(productDto.getName());
           product.setCategory(cat);
           product.setPrice(productDto.getPrice());
           product.setQuantity(productDto.getQuantity());
          return productRepository.save(product);
       }).orElseGet(()-> addProduct(productDto));

    }


    @Override
    public long increaseQuantity(long productId, long quantity) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
        log.info("Product %s quantity successfully increased".formatted(product.getName()));
        return product.getQuantity();
    }

    @Override
    public long reduceQuantity(long productId, long quantity) {
        Product product = productRepository.findById(productId).orElseThrow();
        long oldQuantity = product.getQuantity();
        if (oldQuantity < quantity)
            throw new CustomServiceException("Available quantity too low.", HttpStatus.NOT_ACCEPTABLE);

        product.setQuantity(oldQuantity - quantity);
        productRepository.save(product);
        log.info("Product %s quantity successfully reduced".formatted(product.getName()));
        return product.getQuantity();
    }

    @Override
    public void deleteProductById(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomServiceException("Product not " +
                "exists", HttpStatus.NOT_FOUND));
        productRepository.deleteById(productId);
        log.info("Product %s deleted".formatted(product.getName()));
    }


}
