package longhoang.uet.mobile.closm.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import longhoang.uet.mobile.closm.dtos.response.OrderConfirmResponse;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;
import longhoang.uet.mobile.closm.dtos.request.UpdatePaymentStatusRequest;
import longhoang.uet.mobile.closm.dtos.response.OrderListByStatus;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.services.OrderService;
import longhoang.uet.mobile.closm.services.impl.OrderServiceImpl;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Transactional
    @PostMapping("/confirm-order")
    public ResponseEntity<?> confirmOrder(@RequestBody OrderConfirmationDTO orderConfirmationDTO) {
        try {
            Order order = orderService.confirmOrder(orderConfirmationDTO);
            OrderConfirmResponse orderConfirmResponse = new OrderConfirmResponse();
            orderConfirmResponse.setOrderId(order.getId());
            orderConfirmResponse.setStatus(order.getOrderStatus());
            orderConfirmResponse.setMessage("Order number " + order.getId() + " confirmed.");
            return ResponseEntity.ok(orderConfirmResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getOrderInfo(orderId));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{orderId}/update-payment-status")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestBody UpdatePaymentStatusRequest request) {
        try {
            Order updatedOrder = orderServiceImpl.updatePaymentStatus(orderId, request.getStatus());
            return ResponseEntity.ok(Map.of(
                    "message", "Cập nhật trạng thái thành công",
                    "orderId", updatedOrder.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Đơn hàng không tồn tại");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi cập nhật trạng thái");
        }
    }
}
