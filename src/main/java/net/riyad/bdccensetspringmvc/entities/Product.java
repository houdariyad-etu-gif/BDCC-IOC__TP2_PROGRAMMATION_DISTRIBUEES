package net.riyad.bdccensetspringmvc.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
//pour la construction des objets
public class Product {
    @Id
    @GeneratedValue //pour incrementation du id
    private Long id;
    @NotEmpty
    @Size(min = 3 , max = 50)
    private String name;
    @Min(0)
    private double price;
    @Min(1)
    private double quantity;
    //Annotations JPA-->@Entity | @Id | @GeneratedValue
    //Annotations de validation-->@Size | @Min | @NotEmpty
    //Annotations lombok-->@NoArgsConstructor | @AllArgsConstructor | @Getter | @Setter | @ToString | @Builder

}
