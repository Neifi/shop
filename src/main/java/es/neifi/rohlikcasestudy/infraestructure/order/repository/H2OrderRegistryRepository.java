package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;

public class H2OrderRegistryRepository implements OrderRegistryRepository {

    @Autowired
    private JpaOrderRegistryRepository jpaOrderRegistryRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(OrderRegistry orderRegistry) {
        jpaOrderRegistryRepository.save(new OrderRegistryEntity(
                orderRegistry.orderId().id().toString(),
                orderRegistry.expirationDate()
        ));
    }

    @Override
    public void deleteRegistry(OrderId orderId) {
        jpaOrderRegistryRepository.deleteById(orderId.id().toString());
    }


    private OrderRegistry toOrderRegistry(OrderRegistryEntity orderRegistryEntity) {

        return new OrderRegistry(
                new OrderId(orderRegistryEntity.orderId()),
                orderRegistryEntity.expirationDate()
        );
    }
}
