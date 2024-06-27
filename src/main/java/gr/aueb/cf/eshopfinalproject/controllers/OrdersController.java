package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.dto.InsertOrderDTO;
import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.service.IOrdersService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final IOrdersService ordersService;

    public OrdersController(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrdersDTO> createOrder(@RequestBody InsertOrderDTO insertOrderDTO, Authentication authentication) throws IdNotFoundException {
        try {
            UserDTO userDTO = (UserDTO) authentication.getPrincipal();
            System.out.println(userDTO.getUsername());
            OrdersDTO insertedOrder = ordersService.insertOrder(insertOrderDTO, userDTO.getUsername());
            return ResponseEntity.ok().body(insertedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
