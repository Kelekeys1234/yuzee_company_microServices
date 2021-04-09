package com.yuzee.company.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface MiscellaneousInterface {


	@GET
	@Path("/company/work-with-us/enum/value")
	public Response getWorkWithUsValues();
	

	@GET
	@Path("/company/contact-type/enum/value")
	public Response getContactTypeValues();
	

	@GET
	@Path("/company/company-type/enum/value")
	public Response getCompanyTypeValues();
	

	@GET
	@Path("/company/industry-type/enum/value")
	public Response getIndustryTypeValues();
	

	@GET
	@Path("/company/week-days/enum/value")
	public Response getWeekDaysEnumValues();
	
}
