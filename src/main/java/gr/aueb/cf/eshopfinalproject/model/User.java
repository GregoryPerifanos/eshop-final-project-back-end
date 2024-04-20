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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRSTNAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "USERNAME" , length = 20, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD" , length = 16, nullable = false, unique = false)
    private String password;

    @Column(name = "BALANCE" , nullable = false)
    private Long balance;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<Orders> orders = new ArrayList<>();


}
