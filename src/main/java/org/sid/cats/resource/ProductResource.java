package org.sid.cats.resource;

import io.swagger.annotations.*;
import org.sid.cats.dto.ProductDto;
import org.sid.cats.dto.SearchResponse;
import org.sid.cats.entity.Product;
import org.sid.cats.repository.ProductRepository;
import org.sid.cats.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Product Management", description = "REST APIs related to Product Entity", tags = "Product Management")
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/")

public class ProductResource {

    private final ProductService productService;

    public ProductResource(
            ProductService productService
    ) {
        this.productService = productService;
    }
    @ApiOperation(value = "Save a product", response = Iterable.class, tags = "00 - Save Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @PostMapping("save")
    public ProductDto save(@RequestBody ProductDto p) {
        return this.productService.saveProduct(p);
    }

    @ApiOperation(value = "Get all the products", response = Iterable.class, tags = "01 - Get all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("all")
    public List<ProductDto> getProducts() {
        return this.productService.getAllProducts();
    }

    @ApiOperation(value = "Get products sorted by designation and listed by pages", response = Iterable.class, tags = "02 - Get paginated products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("produits")
    public SearchResponse<ProductDto> getProduits(int page) {
        return this.productService.getProductsByPage(page);
    }

    @ApiOperation(value = "Get products by keyword", response = Iterable.class, tags = "03 - Get products by keyword")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("produitsParMC")
    public  SearchResponse<ProductDto> getProduits(String mc, int page) {
        // exple: http://localhost:3000/produitsParMC?mc=TV&page=0
        // => retourner la/ les produit(s) (max 5 par page) de mot clé contenant TV dans la premiere page
        return this.productService.getProductsByKeywordAndPage(mc, page);

    }

    @ApiOperation(value = "Get a product by reference", response = Iterable.class, tags = "04 - Get a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("getProduit/{reference}")
    public ProductDto getProduit(@PathVariable("reference") long reference) {
        System.out.println(reference);
        // exple: http://localhost:3000/get?ref=1 => retourner produit de reference (id) = 1
        return this.productService.getProduct(reference);
    }

    @ApiOperation(value = "Delete a product", response = Iterable.class, tags = "05 - Delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @DeleteMapping("delete/{reference}")
    public void delete(@PathVariable("reference") long reference) {
        // exple: http://localhost:3000/delete?reference=1 => supprimer le produit de reference (id) = 1
        // et retourne true si il n'ya pas d'erreur durant la suppression
        this.productService.deleteProduct(reference);
    }


    @ApiOperation(value = "Update a product", response = Iterable.class, tags = "06 - Update a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @PutMapping("update/{reference}")
    public ProductDto update(@PathVariable("reference") long reference, @RequestBody ProductDto p) {
        // exple: http://localhost:3000/update?reference=2&designation=xxxx&prix=9000 =>
        // changer le produit par le designation XXXX et prix = 9000 de reference (id) = 2
        return  this.productService.updateProduct(reference,p);
    }

}
