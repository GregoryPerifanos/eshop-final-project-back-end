package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IOrdersService {
    @Transactional
    OrdersDTO insertOrder(OrdersDTO ordersDTO) throws Exception;

    OrdersDTO getOrderById(Long id) throws IdNotFoundException;
    List<OrdersDTO> gelAllOrders();
}
