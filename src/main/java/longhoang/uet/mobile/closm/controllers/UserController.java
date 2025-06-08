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
import org.springframework.http.MediaType;
import java.util.Map;
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

    @PostMapping(
    value    = "/cancel-order",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Map<String,Object>> cancelOrder(@RequestParam Long orderId) {
    try {
        if (orderId == null) {
            return ResponseEntity
              .badRequest()
              .body(Map.of("success", false, "error", "Order id cannot be null"));
        }
        Order order = orderService.getOrder(orderId);
        if (LocalDate.now().isAfter(order.getCancelableDate())) {
            return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(Map.of("success", false, "error", "Quá hạn hủy đơn"));
        }
        orderService.cancelOrder(order);
        return ResponseEntity
          .ok(Map.of("success", true, "message", "Order number " + orderId + " cancelled."));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("success", false, "error", e.getMessage()));
    }
}
}
