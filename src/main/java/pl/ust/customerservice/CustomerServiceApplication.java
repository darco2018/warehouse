package pl.ust.customerservice;

import java.util.stream.Stream;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	
	@Configuration
	class JerseyConfiguration{
		
		@Bean
		CustomerResource customerResource(CustomerRepository repository) {
			return new CustomerResource(repository);
		}
		
		@Bean
		ResourceConfig config(CustomerResource cr) {
			ResourceConfig resourceConfig = new ResourceConfig();
			resourceConfig.register(cr);
			resourceConfig.register(new GenericExceptionMapper());
			return resourceConfig;
		}
	}
	
	@Bean
	ApplicationRunner date (CustomerRepository repo) {
		return args -> Stream.of("cus1", "cus2", "cus3")
							.forEach(cus -> repo.save(new Customer(null, cus )));
		
		/*return new ApplicationRunner() {
		@Override
		public void run(ApplicationArguments args) throws Exception {
			Stream.of("cus1", "cus2", "cus3")
			.forEach(cus -> repo.save(new Customer(null, cus )));
		}
		};*/
	}
	
	@Component
	class GenericExceptionMapper implements ExceptionMapper<IllegalArgumentException>{

		@Override
		public Response toResponse(IllegalArgumentException exception) {
			return Response.serverError().entity(exception.getMessage()).build();
		}
		
	}
	
	
}
