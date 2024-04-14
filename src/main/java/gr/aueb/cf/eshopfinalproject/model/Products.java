package gr.aueb.cf.eshopfinalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERIALNUMBER", length = 80, nullable = false)
    private String serialNumber;

    @Column(name = "NAME" , length = 100, nullable = false, unique = true)
    private String name;
    @Column(name = "PRICE", precision = 10, scale = 2, nullable = false)
    private double price;



    private Long quantity;       // ειναι το FK αυτο
    private String description;  // ειναι το FK αυτο




}
