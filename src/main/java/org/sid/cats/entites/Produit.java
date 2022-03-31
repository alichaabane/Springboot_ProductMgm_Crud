package org.sid.cats.entites;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reference;

    @Column(name = "designation")
    private String designation;

    @Column(name = "prix")
    private double prix;

    @Column(name = "date")
    private String date;

}