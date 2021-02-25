package com.yuzee.company.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.dto.CompanyLocationDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyLocationInterface {
	
	@POST
	@Path("/company/{companyId}/location")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addCompanyLocation(@HeaderParam("userId") String userId ,@PathParam("companyId") String companyId, @Valid @NotNull(message = "Request body should not be null") CompanyLocationDto companyLocationDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@PUT
	@Path("/company/{companyId}/location/{locationId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response updateCompanyLocation(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("locationId") String locationId,  @Valid @NotNull(message = "Request body should not be null") CompanyLocationDto companyLocationDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/location")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getAllCompanyLocation(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/location/{locationId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response deleteCompanyLocation(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("locationId") String locationId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;
	
}
