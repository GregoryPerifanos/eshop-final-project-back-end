package gr.aueb.cf.eshopfinalproject.repository;

import gr.aueb.cf.eshopfinalproject.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
