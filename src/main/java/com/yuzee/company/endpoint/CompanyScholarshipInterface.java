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

import com.yuzee.company.dto.CompanyScholarshipDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface CompanyScholarshipInterface {
	
	@POST
	@Path("/company/{companyId}/scholarship")
	public Response addCompanyScholarship(@HeaderParam("userId") String userId ,@PathParam("companyId") String companyId, @Valid  @NotNull(message = "Request body should not be null/empty") CompanyScholarshipDto companyScholarshipDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@PUT
	@Path("/company/{companyId}/scholarship/{scholarshipId}")
	public Response updateCompanyScholarship(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("scholarshipId") String scholarshipId, @Valid  @NotNull(message = "Request body should not be null/empty") CompanyScholarshipDto companyScholarshipDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/scholarship")
	public Response getAllCompanyScholarship(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/scholarship/{scholarshipId}")
	public Response deleteCompanyScholarship(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("scholarshipId") String scholarshipId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;
	

}
