package com.yuzee.company.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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

import com.yuzee.company.dto.CompanyInternshipDto;
import com.yuzee.company.dto.CompanyStaffInterviewDto;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyStaffInterviewInterface {
	
	@POST
	@Path("/company/{companyId}/staff-interview")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addCompanyStaffInterview(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,  @NotNull(message = "Request body should not be null/empty") @Valid CompanyStaffInterviewDto companyStaffInterviewDto) throws NotFoundException, UnauthorizeException;

	@PUT
	@Path("/company/{companyId}/staff-interview/{staffInterviewId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response updateCompanyStaffInterview(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("staffInterviewId") String staffInterviewId, @NotNull(message = "Request body should not be null/empty") @Valid CompanyStaffInterviewDto companyStaffInterviewDto) throws NotFoundException, UnauthorizeException;

	@GET
	@Path("/company/{companyId}/staff-interview")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getAllCompanyStaffInterview(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/staff-interview/{staffInterviewId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response deleteCompanyStaffInterview(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("staffInterviewId") String staffInterviewId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;
	
	@GET
	@Path("/company/{companyId}/staff-interview/{staffInterviewId}")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getCompanyStaffInterview(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("staffInterviewId") String staffInterviewId) throws NotFoundException, ServiceInvokeException;


}
