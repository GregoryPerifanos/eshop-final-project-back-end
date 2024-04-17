package gr.aueb.cf.eshopfinalproject.repository;

import gr.aueb.cf.eshopfinalproject.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
