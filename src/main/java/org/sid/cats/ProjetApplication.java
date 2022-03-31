package org.sid.cats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class ProjetApplication {
   public static void main(String[] args) {

       System.setProperty("spring.devtools.restart.enabled", "true");
       SpringApplication.run(ProjetApplication.class, args);
   }
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

// Commenter le code au dessus et réactiver le code suivant pour ajouter des données directement à la base (pour tester) : (PS : verifier le port de la base MySQL dans application.properties

// @SpringBootApplication
// public class ProjetApplication implements CommandLineRunner {
//
//    @Autowired
//    private IProduitRepository produitRepository;
//
//    public static void main(String[] args) {
//        SpringApplication.run(ProjetApplication.class, args);
//    }
//
//
//    // for testing the save of products :
//    @Override
//    public void run(String[] args) throws Exception {
//        produitRepository.save(new Produit(null, "H-PC HP", 2800,"10-12-2020"));
//        produitRepository.save(new Produit(null, "H-TV", 4000, "06-05-2001"));
//        produitRepository.save(new Produit(null, "H-Huawei", 1200,"05-03-1993"));
//        produitRepository.save(new Produit(null, "H-PC DELL", 2800,"05-03-1990"));
//        produitRepository.save(new Produit(null, "H-TV SMART", 4000,"05-03-1992"));
//        produitRepository.save(new Produit(null, "H-Huawei Y8", 1200,"05-03-1987"));
//
//    }
// }