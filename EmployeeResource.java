package com.Jerseyy;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employees")
public class EmployeeResource {
	EmployeeRepository repo = new EmployeeRepository();

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Employee> getEmployees() {
		return repo.getEmployeeList();

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Employee getOneEmployee(@PathParam("id") int id) {

		return repo.getEmployee(id);

	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Employee createEmployee(Employee employee) {
		repo.insert(employee);
		return employee;
	}

	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Employee updateEmployee(Employee a1) {
		repo.update(a1);
		return a1;
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_HTML)
	public String delEmployee(@PathParam("id") int id) {
		repo.delete(id);
		String output = "<p>Deleted</p>";
		return output;

	}
}
