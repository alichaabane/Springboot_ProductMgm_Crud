package org.sid.cats.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private Long reference;

    private String designation;

    private double prix;

    private String date;

}
