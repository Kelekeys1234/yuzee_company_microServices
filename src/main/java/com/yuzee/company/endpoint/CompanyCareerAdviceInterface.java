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

import com.yuzee.company.dto.CompanyCareerAdviceDto;
import com.yuzee.company.dto.CompanyPreferredCourseDetailsDto;
import com.yuzee.company.dto.EmployedUserDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface CompanyCareerAdviceInterface {
	
	@POST
	@Path("/company/{companyId}/career-advice")
	public Response addCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId,@Valid  @NotNull(message = "Request body should not be null/empty") CompanyCareerAdviceDto companyCareerAdviceDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@PUT
	@Path("/company/{companyId}/career-advice/{careerAdviceId}")
	public Response updateCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("careerAdviceId") String careerAdviceId, @Valid  @NotNull(message = "Request body should not be null/empty")  CompanyCareerAdviceDto companyCareerAdviceDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@GET
	@Path("/company/{companyId}/career-advice")
	public Response getAllCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId) throws NotFoundException;

	@DELETE
	@Path("/company/{companyId}/career-advice/{careerAdviceId}")
	public Response deleteCareerAdvice(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("careerAdviceId") String careerAdviceId) throws NotFoundException, ServiceInvokeException, UnauthorizeException;

	@POST
	@Path("/company/{companyId}/career-advice/{careerAdviceId}/employed-user")
	public Response addUpdateCareerAdviceEmployedUser(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("careerAdviceId") String careerAdviceId,  @Valid  @NotEmpty(message = "Request body should not be null/empty")  EmployedUserDto employedUserDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	@POST
	@Path("/company/{companyId}/career-advice/{careerAdviceId}/preferred-course")
	public Response addUpdateCareerAdvicePreferredCourse(@HeaderParam("userId") String userId, @PathParam("companyId") String companyId , @PathParam("careerAdviceId") String careerAdviceId,  @Valid  @NotEmpty(message = "Request body should not be null/empty")  CompanyPreferredCourseDetailsDto companyPreferredCourseDetailsDto) throws NotFoundException, UnauthorizeException, BadRequestException;

	
}
