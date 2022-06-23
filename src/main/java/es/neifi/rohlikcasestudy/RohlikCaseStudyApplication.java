package es.neifi.rohlikcasestudy;

import es.neifi.rohlikcasestudy.infraestructure.order.task.AutoCancellationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class RohlikCaseStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RohlikCaseStudyApplication.class, args);
	}




}
