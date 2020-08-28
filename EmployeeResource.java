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
	EmployeeRepository repo=new EmployeeRepository();
	@GET
	@Produces(MediaType.APPLICATION_XML)
	
public List<Employee> getEmpl(){
	return repo.getEmps();
	
}
	
	@GET
	@Path("emp/{id}")
	@Produces(MediaType.APPLICATION_XML)
	
public Employee getEmployee1(@PathParam("id") int id){
	
	return repo.getEmployee(id);
	
}

@POST
@Path("emp")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public Employee createEmployee(Employee a1){
	boolean val=false;
	val=repo.create(a1);
	if(val)
	return a1;
	else 
		return null;
	
}
@PUT
@Path("emp")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)

public Employee updateEmployee(Employee a1){
	boolean val1=false;
	val1=repo.update(a1);
	if(val1)
	return a1;
	else 
		return null;
	
}
@DELETE
@Path("emp/{id}")

public boolean delEmployee(@PathParam("id") int id){
	boolean val=false;
	
		
		val=repo.delete(id);
		
	return val;


}
}
