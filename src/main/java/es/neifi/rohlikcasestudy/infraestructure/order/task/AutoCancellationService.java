package es.neifi.rohlikcasestudy.infraestructure.order.task;

import es.neifi.rohlikcasestudy.application.order.AutoCancellationTask;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistryCreated;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Service

public class AutoCancellationService {
    @Value("${order.ORDER_AUTO_CANCELLATION_TIME}")
    private final int ORDER_AUTO_CANCELLATION_TIME_RATE = 30;
    @Autowired
    private EventBus eventBus;
    @Autowired
    private OrderRegistryRepository orderRegistryRepository;

    @Async
    @Scheduled(fixedRate = ORDER_AUTO_CANCELLATION_TIME_RATE, timeUnit = TimeUnit.SECONDS)

    @KafkaListener(id = "autocancellationService",
            topicPartitions =
                    {@TopicPartition(topic = "orderRegistry",
                            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))})
    public void scheduleFixedRateTaskAsync(OrderRegistryCreated orderRegistryCreated) {
        new AutoCancellationTask(eventBus).execute(orderRegistryCreated);
    }


}

