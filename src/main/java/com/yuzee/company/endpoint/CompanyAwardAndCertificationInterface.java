package com.yuzee.company.endpoint;

import javax.validation.Valid;
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

import com.yuzee.company.dto.CompanyAwardAndCertificationDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface CompanyAwardAndCertificationInterface {
	
	@POST
	@Path("/company/{companyId}/award-and-certification")
	public Response addCompanyAwardAndCertification(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid CompanyAwardAndCertificationDto companyAwardAndCertificationDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@PUT
	@Path("/company/{companyId}/award-and-certification/{awardCertificationId}")
	public Response updateCompanyAwardAndCertification(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("awardCertificationId") String awardCertificationId,@Valid CompanyAwardAndCertificationDto companyAwardAndCertificationDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/award-and-certification")
	public Response getAllAwardAndCertification(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/award-and-certification/{awardCertificationId}")
	public Response deleteCompanyAwardAndCertification(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("awardCertificationId") String awardCertificationId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;
}