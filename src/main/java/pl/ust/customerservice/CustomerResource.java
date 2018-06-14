package pl.ust.customerservice;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
	
	private final CustomerRepository customerRepository;
	
	public CustomerResource(CustomerRepository repository) {
		this.customerRepository = repository;
	}
	
	@GET
	public Collection<Customer> customers(){
		return this.customerRepository.findAll();
	}

	@GET
	@Path("{id}")
	public Customer byId(@PathParam("id") long id) {
		return this.customerRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("couldn't find the record with id " + id));
	}

}
