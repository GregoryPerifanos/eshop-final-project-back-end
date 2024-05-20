package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;

import java.util.List;

public interface IOrdersService {
    OrdersDTO getOrderById(Long id) throws IdNotFoundException;
    List<OrdersDTO> gelAllOrders();
}
