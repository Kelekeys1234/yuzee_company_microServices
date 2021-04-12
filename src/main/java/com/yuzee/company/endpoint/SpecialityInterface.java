package com.yuzee.company.endpoint;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.yuzee.company.dto.SpecialityDto;

@Path("/api/v1")
@Consumes({ "application/json", "application/xml" })
@Produces({ "application/json", "application/xml" })
public interface SpecialityInterface {
	
	@GET
	@Path("/speciality")
	public Response getAllSpeciality();
	
	@POST
	@Path("/admin/speciality")
	public Response addSpeciality(@Valid @NotNull List<SpecialityDto> listOfSpeciality);

}
