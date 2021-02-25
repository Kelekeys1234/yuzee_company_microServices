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

import com.yuzee.company.dto.CompanyAboutUsDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
public interface CompanyAboutUsInterface {
	

	@POST
	@Path("/company/{companyId}/about-us")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response addUpdateCompanyAboutUs(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid @NotNull(message = "Request body should not be null") CompanyAboutUsDto companyAboutUsDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/about-us")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public Response getCompanyAboutUs(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException, ServiceInvokeException;
}
