package org.sid.cats.controllers;

import org.sid.cats.repositories.IProduitRepository;
import org.sid.cats.entites.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class produitController {
    private final IProduitRepository produitRepository;

    @Autowired
    public produitController(IProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @RequestMapping("/save")
    public Produit saveProduit(Produit p) {

        return produitRepository.save(p);
    }

    @RequestMapping("/all")
    public List<Produit> getProduit() {
        return produitRepository.findAll(sortByReferenceAsc());
    }

    private Sort sortByReferenceAsc() {
        Sort sort = Sort.by("reference");
        return sort;
    }

    @RequestMapping("/produits")
    public Page<Produit> getProduits(int page) {
        Sort sort = Sort.by("designation");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return produitRepository.findAll(pageable);
    }

    @RequestMapping("/produitsParMC")
    public Page<Produit> getProduits(String mc, int page) {
        // exple: http://localhost:3000/produitsParMC?mc=TV&page=0
        // => retourne la/ les produit(s) (max 5 par page) de mot clé contenant TV dans la premiere page

        Sort sort = Sort.by("reference");
        Pageable pageable = PageRequest.of(page, 5, sort);

        return produitRepository.produitParMC("%" + mc + "%", pageable);
    }

    @RequestMapping("/getProduit")
    public Produit getProduit(Long ref) {
        System.out.println(ref);
        // exple: http://localhost:3000/get?ref=1 => retourne produit de reference (id) = 1
        return produitRepository.findById(ref).orElse(null);
    }

    @RequestMapping("/delete")
    public boolean delete(Long reference) {
        // exple: http://localhost:3000/delete?reference=1 => supprime le produit de reference (id) = 1
        // et retourne true si il n'ya pas d'erreur durant la suppression
        produitRepository.deleteById(reference);
        return true;
    }

    @RequestMapping("/update")
    public Produit update(Produit p) {
        // exple: http://localhost:3000/update?reference=2&designation=xxxx&prix=9000 =>
        // changer le produit par le designation XXXX et prix = 9000 de reference (id) = 2
        return produitRepository.saveAndFlush(p);
    }

}
