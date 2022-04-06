package org.sid.cats.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.cats.dto.ProductDto;
import org.sid.cats.dto.SearchResponse;
import org.sid.cats.entity.Product;
import org.sid.cats.repository.ProductRepository;
import org.sid.cats.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductDto p) {
        Product product = mapProductDtoToProduct(p);
        return this.mapProductToProductDto(this.productRepository.save(product));
    }

    //TODO : try understand that instruction of mapping
    @Override
    public List<ProductDto> getAllProducts() {
        return this.productRepository.findAll(sortByReferenceAsc()).stream().map(this::mapProductToProductDto).collect(Collectors.toList());
    }

    private Sort sortByReferenceAsc() {
        return Sort.by("reference");
    }

    @Override
    public SearchResponse<ProductDto> getProductsByPage(int page) {
        Sort sort = Sort.by("designation");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<Product> productPage = this.productRepository.findAll(pageable);

        SearchResponse<ProductDto> response = new SearchResponse<>();
        response.setData(productPage.getContent().stream().map(this::mapProductToProductDto).collect(Collectors.toList()));
        response.setTotalRecords(productPage.getTotalElements());
        System.out.println(response);
        return response;
    }

    //TODO  Pagination

    @Override
    public SearchResponse<ProductDto> getProductsByKeywordAndPage(String keyword, int page) {
        Sort sort = Sort.by("designation");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<Product> productPage = productRepository.productParMC("%" + keyword + "%", pageable);

        SearchResponse<ProductDto> response = new SearchResponse<>();
        response.setData(productPage.getContent().stream().map(this::mapProductToProductDto).collect(Collectors.toList()));
        response.setTotalRecords(productPage.getTotalElements());
        return response;
    }

    @Override
    public ProductDto getProduct(long reference) {
        return this.mapProductToProductDto(this.productRepository.findById(reference).orElseThrow(IllegalArgumentException::new));
    }

    //TODO Update Product
    @Override
    public ProductDto updateProduct(long reference, ProductDto p) {
        Product product = new Product();
        if (p.getReference() != null) {
            product = productRepository.findById(p.getReference()).orElseThrow(IllegalArgumentException::new);
        }
        product = productRepository.save(product);
        return mapProductToProductDto(product);
    }

    @Override
    public void deleteProduct(long reference) {
        this.productRepository.deleteById(reference);
    }


    @Override
    public Product mapProductDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setReference(productDto.getReference());
        product.setDesignation(productDto.getDesignation());
        product.setPrix(productDto.getPrix());
        product.setDate(productDto.getDate());

        return product;
    }

    @Override
    public ProductDto mapProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setReference(product.getReference());
        productDto.setDesignation(product.getDesignation());
        productDto.setPrix(product.getPrix());
        productDto.setDate(product.getDate());

        return productDto;
    }


}
