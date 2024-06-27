package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.InsertOrderDTO;
import gr.aueb.cf.eshopfinalproject.dto.OrdersDTO;
import gr.aueb.cf.eshopfinalproject.dto.ProductsDTO;
import gr.aueb.cf.eshopfinalproject.model.Orders;
import gr.aueb.cf.eshopfinalproject.model.Products;
import gr.aueb.cf.eshopfinalproject.model.Sales;
import gr.aueb.cf.eshopfinalproject.model.User;
import gr.aueb.cf.eshopfinalproject.repository.OrdersRepository;
import gr.aueb.cf.eshopfinalproject.repository.ProductsRepository;
import gr.aueb.cf.eshopfinalproject.repository.SalesRepository;
import gr.aueb.cf.eshopfinalproject.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final SalesRepository salesRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, UserRepository userRepository, SalesRepository salesRepository, ProductsRepository productsRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.salesRepository = salesRepository;
        this.productsRepository = productsRepository;
    }


    @Transactional
    @Override
    public OrdersDTO insertOrder(InsertOrderDTO insertOrderDTO, String username) throws Exception{
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new RuntimeException("User does not exists");
            }
            User currentUser = user.get();
            List<ProductsDTO> productsList = insertOrderDTO.getProductsDTOList();
            Double totalValue = 0.0;
            for (ProductsDTO productsDTO : productsList) {
               totalValue += productsDTO.getPrice();
            }

            Long totalValueLong = Math.round(totalValue);

            if (totalValue > currentUser.getBalance()) {
                throw new RuntimeException("Insufficient balance");
            }

            currentUser.setBalance(currentUser.getBalance() - totalValueLong);
            userRepository.save(currentUser);

            Orders orders = new Orders();
            orders.setUser(currentUser);
            Orders insertedOrders = ordersRepository.saveAndFlush(orders);
            for (ProductsDTO productsDTO : productsList) {
                Sales sales = new Sales();
                sales.setQuantity(1L);
                sales.setOrder(insertedOrders);
                sales.setProduct(productsRepository.getOne(productsDTO.getId()));
                salesRepository.save(sales);
            }
            return convertToOrdersDTO(insertedOrders);
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
        orders.setId(orders.getId());
        return orders;
    }

    private OrdersDTO convertToOrdersDTO(Orders orders) {
       OrdersDTO ordersDTO = new OrdersDTO();
       ordersDTO.setId(orders.getId());
       return ordersDTO;
    }
}
