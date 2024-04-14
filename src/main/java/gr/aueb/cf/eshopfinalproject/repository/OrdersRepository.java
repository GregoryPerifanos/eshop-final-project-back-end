package gr.aueb.cf.eshopfinalproject.repository;

import gr.aueb.cf.eshopfinalproject.model.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {
}
