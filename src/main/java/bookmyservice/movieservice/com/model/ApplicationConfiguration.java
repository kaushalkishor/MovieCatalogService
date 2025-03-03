package bookmyservice.movieservice.com.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfiguration {

	@Bean
	@Primary
	RestTemplate restTemplate() {
		return new RestTemplate();

	}

	@Bean
	WebClient.Builder getWebclientBuilder() {
		return WebClient.builder();
	}

}
