package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orderPrice")
    public ResponseEntity<?> calculateOrderPrice(OrderRequestDTO orderRequestDTO) {
        try {
            return ResponseEntity.ok(orderService.calculateOrderPrice(orderRequestDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
