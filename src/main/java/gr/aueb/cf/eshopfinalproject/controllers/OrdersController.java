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

/**
 * OrdersController handles order-related HTTP requests and responses.
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final IOrdersService ordersService;

    /**
     * Constructs an OrdersController with the specified IOrdersService.
     *
     * @param ordersService the orders service to be used
     */
    public OrdersController(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * Creates a new order.
     *
     * @param insertOrderDTO the order data transfer object containing order details
     * @param authentication the authentication object containing the user's authentication details
     * @return the created order
     * @throws IdNotFoundException if the user ID is not found
     */
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

    /**
     * Retrieves an order by its ID.
     *
     * @param ordersId the ID of the order to be retrieved
     * @return the order with the specified ID
     */
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

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
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
