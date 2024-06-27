package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.InsertOrderDTO;
import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IOrdersService {
    @Transactional
    OrdersDTO insertOrder(InsertOrderDTO insertOrderDTO, String username) throws Exception;

    OrdersDTO getOrderById(Long id) throws IdNotFoundException;
    List<OrdersDTO> gelAllOrders();
}
