package com.yuzee.company.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.dto.CompanyDto;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyInterface {
	
	@POST
	@Path("/company/initial-info")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addCompanyInitialInfo(@HeaderParam("userId") String userId, @Valid  @NotNull(message = "Request body should not be null") CompanyDto companyDto);

	@PUT
	@Path("/company/{companyId}/initial-info")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response updateCompanyInitialInfo(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId ,@Valid  @NotNull(message = "Request body should not be null") CompanyDto companyDto) throws NotFoundException, UnauthorizeException;
	
	
	@GET
	@Path("/company/{companyId}/initial-info")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getCompanyInitialInfo(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException, ServiceInvokeException;
	
}
