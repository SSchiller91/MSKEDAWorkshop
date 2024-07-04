package KafkaSpringBoot.example.demo;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import com.github.javafaker.Faker;
import java.util.stream.Stream;


@SpringBootApplication
public class KafkaSpringBootApplication {
	public static void main(String[] args) {SpringApplication.run(KafkaSpringBootApplication.class, args);}
}

@RequiredArgsConstructor
@Component
class Producer {
	private final KafkaTemplate<Integer, String> template;
	Faker faker;
	@EventListener(ApplicationStartedEvent.class)
   	public void generate() {
    	faker = Faker.instance();
     	final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));
    	final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));
     	Flux.zip(interval, quotes)
      		.map(it -> template.send("hobbit", faker.random().nextInt(42), it.getT2())).blockLast();
   	}
}
/* 
@Component
class Consumer {
	@KafkaListener(topics= {"hobbit"}, groupId="spring-boot-kafka")
	public void consume(String quote) {
		System.out.println("received= " + quote);
	} 
} 
*/

