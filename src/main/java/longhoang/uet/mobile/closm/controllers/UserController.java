package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.dtos.response.OrderListByStatus;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.services.OrderService;
import longhoang.uet.mobile.closm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
