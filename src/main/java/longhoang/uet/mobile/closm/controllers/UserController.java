package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.dtos.response.orderDTO.OrderListByStatus;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.services.OrderService;
import longhoang.uet.mobile.closm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {
        try {
            return ResponseEntity.ok().body(userService.getUserDTO(email));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/orderList")
    public ResponseEntity<?> getUserOrderList(@RequestParam String userEmail) {
        List<OrderListByStatus> orderListByStatus = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            try {
                orderListByStatus.add(orderService.getUserOrderListByOrderStatus(orderStatus, userEmail));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(orderListByStatus, HttpStatus.OK);
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestParam Long orderId) {
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
