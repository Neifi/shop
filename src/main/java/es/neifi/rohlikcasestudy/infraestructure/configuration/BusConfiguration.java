package es.neifi.rohlikcasestudy.infraestructure.configuration;

import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import es.neifi.rohlikcasestudy.infraestructure.bus.SpringEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusConfiguration {

    @Bean
    public EventBus eventBus(){
        return new SpringEventBus();
    }
}
