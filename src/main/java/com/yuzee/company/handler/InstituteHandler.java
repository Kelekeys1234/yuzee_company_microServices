package com.yuzee.company.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.company.constant.Constants;
import com.yuzee.company.dto.GenericResponseDto;
import com.yuzee.company.dto.InstituteBasicInfoDto;
import com.yuzee.company.exception.ServiceInvokeException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstituteHandler {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GET_INSTITUTE_BY_ID = "/api/v1/public/basic/info";

	public InstituteBasicInfoDto getInstituteById(String instituteId) throws ServiceInvokeException {
		ResponseEntity<GenericResponseDto<InstituteBasicInfoDto>> responseEntity = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constants.INSTITUTE_BASE_PATH).append(GET_INSTITUTE_BY_ID).append("/").append(instituteId);

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<>(headers);
			
			responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request,
					new ParameterizedTypeReference<GenericResponseDto<InstituteBasicInfoDto>>() {});

			if (responseEntity.getStatusCode().value() != 200) {
				log.error("Error response recieved from Institute service with error code {}",
						responseEntity.getStatusCode().value());
				throw new ServiceInvokeException("Error response recieved from Institute service with error code "
						+ responseEntity.getStatusCode().value());
			}
		} catch (Exception e) {
			log.error("Error invoking Institute service {}", e);
			if (e instanceof ServiceInvokeException) {
				throw e;
			} else {
				throw new ServiceInvokeException("Error invoking Institute service");
			}
		}
		return responseEntity.getBody().getData();
	}
	
}
