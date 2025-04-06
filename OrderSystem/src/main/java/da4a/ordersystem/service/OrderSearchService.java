package da4a.ordersystem.service;

import da4a.ordersystem.model.Order;
import da4a.ordersystem.model.Payment;
import da4a.ordersystem.model.paymentType.*;
import da4a.ordersystem.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSearchService {
    private final OrderRepository orderRepository;

    public List<Order> searchOrders(
            String customerName,
            String city,
            String street,
            String zipcode,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            String paymentType,
            String orderStatus,
            String paymentStatus) {

        Specification<Order> spec = Specification.where(null);

        if (customerName != null && !customerName.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("customer").get("name")), "%" + customerName.toLowerCase() + "%"));
        }

        if (city != null && !city.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("customer").get("address").get("city")), city.toLowerCase()));
        }

        if (street != null && !street.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("customer").get("address").get("street")), "%" + street.toLowerCase() + "%"));
        }

        if (zipcode != null && !zipcode.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("customer").get("address").get("zipcode"), zipcode));
        }

        if (fromDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("date"), fromDate));
        }

        if (toDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("date"), toDate));
        }

        if (orderStatus != null && !orderStatus.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), orderStatus));
        }

        if (paymentType != null && !paymentType.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                // Join with the payment table and check the type
                return cb.equal(
                        cb.treat(root.get("payment"), getPaymentClass(paymentType)).get("id"),
                        root.get("payment").get("id")
                );
            });
        }

        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                return cb.equal(root.get("payment").get("status"), paymentStatus);
            });
        }

        return orderRepository.findAll(spec);
    }

    private Class<?> getPaymentClass(String paymentType) {
        return switch (paymentType.toLowerCase()) {
            case "cash" -> Cash.class;
            case "check" -> Check.class;
            case "credit" -> Credit.class;
            default -> Payment.class;
        };
    }
}
