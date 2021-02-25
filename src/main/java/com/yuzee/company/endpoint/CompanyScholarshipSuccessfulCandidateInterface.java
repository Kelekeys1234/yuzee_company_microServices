package com.yuzee.company.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.dto.CompanyScholarshipGenericDto;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyScholarshipSuccessfulCandidateInterface {
	
	@POST
	@Path("/company/{companyId}/scholarship/{scholarshipId}/scholarship-successful-candidate")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addUpdateCompanyScholarshipSuccessfulCandidate(@HeaderParam("userId") String userId ,@PathParam("companyId") String companyId,@PathParam("scholarshipId") String scholarshipId, @Valid  @NotNull(message = "Request body should not be null/empty") CompanyScholarshipGenericDto companyScholarshipGenericDto) throws NotFoundException, UnauthorizeException;

	@GET
	@Path("/company/{companyId}/scholarship/{scholarshipId}/scholarship-successful-candidate")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getCompanyScholarshipSuccessfulCandidate(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@PathParam("scholarshipId") String scholarshipId) throws NotFoundException;


}
