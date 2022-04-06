package org.sid.cats.service;

import org.sid.cats.dto.ProductDto;
import org.sid.cats.dto.SearchResponse;
import org.sid.cats.entity.Product;
import java.util.List;

public interface ProductService {


    ProductDto saveProduct(ProductDto p);

    List<ProductDto> getAllProducts();

    SearchResponse<ProductDto> getProductsByPage(int page);

    SearchResponse<ProductDto> getProductsByKeywordAndPage(String keyword, int page);

    ProductDto getProduct(long reference);

    void deleteProduct(long reference);

    ProductDto updateProduct(long reference, ProductDto p);

    Product mapProductDtoToProduct(ProductDto productDto);

    ProductDto mapProductToProductDto(Product product);

}
