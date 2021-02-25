package com.yuzee.company.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.dto.CompanyWorkWithUsDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyWorkWithUsInterface {
	
	@GET
	@Path("/company/work-with-us/enum/value")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getAllWorkWithUsEnumValues();
	
	@POST
	@Path("/company/{companyId}/work-with-us")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addUpdateCompanyWorkWithUs(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId, 
			@Valid @NotNull(message = "Request body should not be null") CompanyWorkWithUsDto companyWorkWithUsDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/work-with-us")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getAllCompanyWorkWithUs(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;
	
	@DELETE
	@Path("/company/{companyId}/work-with-us/{companyWorkWithUsId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response deleteCompanyWorkWithUs(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,
			@PathParam("companyWorkWithUsId") String companyWorkWithUsId) throws NotFoundException, UnauthorizeException;


	
}
