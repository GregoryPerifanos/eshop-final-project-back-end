package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.model.Orders;
import gr.aueb.cf.eshopfinalproject.repository.OrdersRepository;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrdersServiceImpl implements IOrdersService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }


    @Transactional
    @Override
    public OrdersDTO insertOrder(OrdersDTO ordersDTO) throws Exception{
        try {
            Orders orders = convertToOrders(ordersDTO);
            if (ordersRepository.existsById(orders.getId())) {
                throw new Exception("Order already exists");
            }
            Orders insertOrder = ordersRepository.save(orders);
            return convertToOrdersDTO(insertOrder);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public OrdersDTO getOrderById(Long id) throws IdNotFoundException {
        try {
            Optional<Orders> orders = ordersRepository.findById(id);
            if (orders.isPresent()) {
                Orders order = orders.get();
                return convertToOrdersDTO(order);
            } else {
                throw new IdNotFoundException(Orders.class, id);
            }

        } catch (Exception e) {
            log.info("Order id not found");
            throw e;
        }
    }

    @Transactional
    @Override
    public List<OrdersDTO> gelAllOrders() {
        List<Orders> orders = ordersRepository.findAll();
        List<OrdersDTO> ordersDTOs = new ArrayList<>();

        for (Orders order : orders) {
            ordersDTOs.add(convertToOrdersDTO(order));
        }
        return ordersDTOs;
    }

    private Orders convertToOrders(OrdersDTO orderDTO) {
        Orders orders = new Orders();
        orders.setOrderNumber(orders.getOrderNumber());
        orders.setId(orders.getId());
        return orders;
    }

    private OrdersDTO convertToOrdersDTO(Orders orders) {
       OrdersDTO ordersDTO = new OrdersDTO();
       ordersDTO.setId(orders.getId());
       ordersDTO.setOrderId(ordersDTO.getOrderId());
       return ordersDTO;
    }
}
