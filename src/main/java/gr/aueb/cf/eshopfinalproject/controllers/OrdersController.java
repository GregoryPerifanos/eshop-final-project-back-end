package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.service.IOrdersService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final IOrdersService ordersService;

    public OrdersController(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/get/{ordersId}")
    public ResponseEntity<OrdersDTO> getOrders(@PathVariable("ordersId") Long ordersId) {
        try {
            OrdersDTO orders = ordersService.getOrderById(ordersId);
            return ResponseEntity.ok(orders);
        } catch (IdNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        try {
            List<OrdersDTO> ordersDTOs = ordersService.gelAllOrders();
            return ResponseEntity.ok(ordersDTOs);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
