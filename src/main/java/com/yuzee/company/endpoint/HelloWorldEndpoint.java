package com.yuzee.company.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/api/v1")
public interface HelloWorldEndpoint {
	
	
	@GET
	@Path("/ping")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public String helloWorld();

}
