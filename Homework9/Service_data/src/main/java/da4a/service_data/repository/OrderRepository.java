package da4a.service_data.repository;

import da4a.service_data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { }