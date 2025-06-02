  package longhoang.uet.mobile.closm.controllers;

import jakarta.transaction.Transactional;
import longhoang.uet.mobile.closm.dtos.mappers.OrderInfoDTO;
import longhoang.uet.mobile.closm.dtos.response.orderDTO.OrderConfirmResponse;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/get-all-orders")
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok().body(orderService.getAllOrderInfo());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

      @PreAuthorize("hasAuthority('ROLE_ADMIN')")
      @PutMapping("/update-order/{id}")
      public ResponseEntity<?> updateOrder(@PathVariable long id, @RequestBody OrderInfoDTO updateOrderInfoDTO) {
          try {
              return ResponseEntity.ok().body(orderService.updateOrder(id, updateOrderInfoDTO));
          } catch (Exception e) {
              e.printStackTrace();
              return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasAuthority('ROLE_ADMIN')")
      @DeleteMapping("/delete-order/{id}")
      public ResponseEntity<?> deleteOrder(@PathVariable long id) {
          try {
              return ResponseEntity.ok().body(orderService.deleteOrder(id));
          } catch (Exception e) {
              e.printStackTrace();
              return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

  }
