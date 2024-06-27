package gr.aueb.cf.eshopfinalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "SERIAL_NUMBER", length = 80, nullable = false)
    private String serialNumber;
    @Column(name = "NAME" , length = 100, nullable = false, unique = true)
    private String name;
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_id")
    private List<Sales> sales = new ArrayList<Sales>();

    public Products(Long id) {
        this.id = id;
    }

}
