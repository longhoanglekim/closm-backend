package longhoang.uet.mobile.closm.controllers;

import jakarta.transaction.Transactional;
import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.dtos.response.OrderConfirmResponse;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.services.OrderService;
import longhoang.uet.mobile.closm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/confirm-order")
    public ResponseEntity<?> confirmOrder(@RequestBody OrderConfirmationDTO orderConfirmationDTO) {
        try {
            Order order = orderService.confirmOrder(orderConfirmationDTO);
            OrderConfirmResponse orderConfirmResponse = new OrderConfirmResponse();
            orderConfirmResponse.setOrderId(order.getId());
            orderConfirmResponse.setStatus(order.getStatus());
            orderConfirmResponse.setMessage("Order number " + order.getId() + " confirmed.");
            return ResponseEntity.ok(orderConfirmResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
