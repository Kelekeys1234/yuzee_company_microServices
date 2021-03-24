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

import com.yuzee.company.dto.CompanyAchievementDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface CompanyAchievementInterface {
	
	@POST
	@Path("/company/{companyId}/achievement")
	public Response addCompanyAchievement(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid  @NotNull(message = "Request body should not be null") CompanyAchievementDto companyAchievementDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@PUT
	@Path("/company/{companyId}/achievement/{achievementId}")
	public Response updateCompanyAchievement(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("achievementId") String achievementId,@Valid  @NotNull(message = "Request body should not be null") CompanyAchievementDto companyAchievementDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/achievement")
	public Response getAllCompanyAchievement(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/achievement/{achievementId}")
	public Response deleteCompanyAchievement(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("achievementId") String achievementId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;
	
	@GET
	@Path("/company/{companyId}/achievement/{achievementId}")
	public Response getCompanyAchievement(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("achievementId") String achievementId) throws NotFoundException, ServiceInvokeException;
}
