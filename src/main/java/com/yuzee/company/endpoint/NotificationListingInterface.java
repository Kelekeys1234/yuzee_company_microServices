package com.yuzee.company.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.exception.BadRequestException;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface NotificationListingInterface {
	
	@GET
	@Path("/achievement/status/{status}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public Response getAllUserCompanyAchievementTagedRequest(@HeaderParam("userId") String userId,@PathParam("status") String status,@PathParam("pageNumber") Integer pageNumber,
			@PathParam("pageSize") Integer pageSize) throws BadRequestException;
	
	@PUT
	@Path("/achievement/{achievementId}/status/{status}")
	public Response changeCompanyAchievementTagedUserStatus(@HeaderParam("userId") String userId,@PathParam("achievementId") String achievementId,
			@PathParam("status") String status) throws BadRequestException;

}
