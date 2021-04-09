package com.yuzee.company.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.dto.CompanyCareerAdviceDto;
import com.yuzee.company.dto.CompanyCultureMissionDto;
import com.yuzee.company.dto.CompanyCultureVisionDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface CompanyCultureInterface {
	
	@POST
	@Path("/company/{companyId}/career-culture-vision")
	public Response addUpdateCompanyCultureVision(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid  @NotNull(message = "Request body should not be null/empty") CompanyCultureVisionDto companyCultureVisionDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@POST
	@Path("/company/{companyId}/career-culture-mission")
	public Response addUpdateCompanyCultureMission(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid  @NotNull(message = "Request body should not be null/empty") CompanyCultureMissionDto companyCultureMissionDto) throws NotFoundException, UnauthorizeException, BadRequestException;


}
