package da4a.service_data.controller;

import da4a.service_data.model.Order;
import da4a.service_data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @AuthenticationPrincipal Jwt jwt) {
        String userSub = jwt.getClaimAsString("sub"); // Извлекаем sub из JWT
        order.setUserSub(userSub); // Устанавливаем userSub в заказ
        return ResponseEntity.ok(orderService.save(order));
    }
}