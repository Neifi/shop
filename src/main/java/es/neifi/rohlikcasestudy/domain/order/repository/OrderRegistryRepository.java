package es.neifi.rohlikcasestudy.domain.order.repository;

import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;

import java.util.stream.Stream;

public interface OrderRegistryRepository {
    void save(OrderRegistry orderRegistry);

    void deleteRegistry(OrderId orderId);

}
