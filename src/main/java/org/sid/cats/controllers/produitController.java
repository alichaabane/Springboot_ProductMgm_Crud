package org.sid.cats.controllers;

import io.swagger.annotations.*;
import org.sid.cats.entites.Produit;
import org.sid.cats.repositories.ProductRepository;
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

public class produitController {
    private final ProductRepository productRepository;

    @Autowired
    public produitController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "Save a product", response = Iterable.class, tags = "00 - Save Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @PostMapping("save")
    public Produit saveProduit(@RequestBody Produit p) {

        return productRepository.save(p);
    }

    @ApiOperation(value = "Get all the products", response = Iterable.class, tags = "01 - Get all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("all")
    public List<Produit> getProduit() {
        return productRepository.findAll(sortByReferenceAsc());
    }

    private Sort sortByReferenceAsc() {
        return Sort.by("reference");
    }

    @ApiOperation(value = "Get products sorted by designation and listed by pages", response = Iterable.class, tags = "02 - Get paginated products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("produits")
    public Page<Produit> getProduits(int page) {
        Sort sort = Sort.by("designation");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return productRepository.findAll(pageable);
    }

    @ApiOperation(value = "Get products by keyword", response = Iterable.class, tags = "03 - Get products by keyword")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("produitsParMC")
    public Page<Produit> getProduits(String mc, int page) {
        // exple: http://localhost:3000/produitsParMC?mc=TV&page=0
        // => retourne la/ les produit(s) (max 5 par page) de mot clé contenant TV dans la premiere page

        Sort sort = Sort.by("reference");
        Pageable pageable = PageRequest.of(page, 5, sort);

        return productRepository.productParMC("%" + mc + "%", pageable);
    }

    @ApiOperation(value = "Get a product by reference", response = Iterable.class, tags = "04 - Get a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @GetMapping("getProduit/{reference}")
    public Produit getProduit(@PathVariable("reference") long reference) {
        System.out.println(reference);
        // exple: http://localhost:3000/get?ref=1 => retourne produit de reference (id) = 1
        return productRepository.findById(reference).orElse(null);
    }

    @ApiOperation(value = "Delete a product", response = Iterable.class, tags = "05 - Delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @DeleteMapping("delete")
    public boolean delete(@PathVariable("reference") long reference) {
        // exple: http://localhost:3000/delete?reference=1 => supprime le produit de reference (id) = 1
        // et retourne true si il n'ya pas d'erreur durant la suppression
        productRepository.deleteById(reference);
        return true;
    }


    @ApiOperation(value = "Update a product", response = Iterable.class, tags = "06 - Update a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success | OK"),
            @ApiResponse(code = 401, message = "Not Authorized !"),
            @ApiResponse(code = 403, message = "Forbidden !"),
            @ApiResponse(code = 404, message = "Not Found !") ,
            @ApiResponse(code = 500, message = "Internal Server Error !") })
    @PutMapping("update/{reference}")
    public Produit update(@PathVariable("reference") long reference, @RequestBody Produit p) {
        // exple: http://localhost:3000/update?reference=2&designation=xxxx&prix=9000 =>
        // changer le produit par le designation XXXX et prix = 9000 de reference (id) = 2
        return productRepository.save(p);
    }

}
