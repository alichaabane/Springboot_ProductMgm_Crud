package org.sid.cats.repositories;

import org.sid.cats.entites.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Produit, Long> {
    @Query("select p from Produit p where p.designation like :x")
     Page<Produit> productParMC(@Param("x") String mc, Pageable p);
     List<Produit> findByDesignation(String des);
     Page<Produit> findByDesignation(String des, Pageable p);
}
