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

import com.yuzee.company.dto.CompanyCareerAdviceDto;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyCareerAdviceInterface {
	
	@POST
	@Path("/company/{companyId}/career-advice")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid  @NotNull(message = "Request body should not be null/empty") CompanyCareerAdviceDto companyCareerAdviceDto) throws NotFoundException, UnauthorizeException;

	@PUT
	@Path("/company/{companyId}/career-advice/{careerAdviceId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response updateCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("careerAdviceId") String careerAdviceId, @Valid  @NotNull(message = "Request body should not be null/empty")  CompanyCareerAdviceDto companyCareerAdviceDto) throws NotFoundException, UnauthorizeException;

	@GET
	@Path("/company/{companyId}/career-advice")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getAllCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/career-advice/{careerAdviceId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response deleteCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("careerAdviceId") String careerAdviceId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;

}
