  package longhoang.uet.mobile.closm.controllers;

import jakarta.transaction.Transactional;
import longhoang.uet.mobile.closm.dtos.response.OrderConfirmResponse;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

  @RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

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


    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            if (orderId == null) {
                return new ResponseEntity<>("Order id cannot be null", HttpStatus.BAD_REQUEST);
            }
            Order order = orderService.getOrder(orderId);
            if (LocalDate.now().isAfter(order.getCancelableDate())) {
                throw new Exception("Đơn hàng không thể hủy vì đã quá thời hạn cho phép.");
            }
            orderService.cancelOrder(order);
            return new ResponseEntity<>("Order number " + orderId + " cancelled.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
