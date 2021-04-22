package com.yuzee.company.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.company.constant.Constants;
import com.yuzee.company.dto.ResponseDtoWrapper;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageHandler {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GET_STORAGE_BY_ID = "/api/v1/storage";
	private static final String DELETE_BY_ID = "/api/v1/storage/entityId/";

	public List<StorageDto> getStoragesResponse(String entityId, EntityTypeEnum entityType,
			EntitySubTypeEnum entitySubType, List<String> privacyLevels) throws ServiceInvokeException, NotFoundException {
		List<String> entityIds = new ArrayList<>();
		entityIds.add(entityId);
		return getStoragesResponse(entityIds, entityType, entitySubType,privacyLevels);
	}
	
	public List<StorageDto> getStoragesResponse(List<String> entityIds, EntityTypeEnum entityType,
			EntitySubTypeEnum entitySubType , List<String> privacyLevels) throws ServiceInvokeException, NotFoundException {
		List<EntitySubTypeEnum> entitySubTypeEnums = new ArrayList<>();
		entitySubTypeEnums.add(entitySubType);
		return getStoragesResponse(entityIds, entityType, entitySubTypeEnums,privacyLevels);
	}
	
	public List<StorageDto> getStoragesResponse(List<String> entityIds, EntityTypeEnum entityType,
			List<EntitySubTypeEnum> entitySubTypeEnums, List<String> privacyLevels) throws ServiceInvokeException, NotFoundException {
		ResponseEntity<ResponseDtoWrapper<List<StorageDto>>> getStoragesResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constants.STORAGE_BASE_PATH).append(GET_STORAGE_BY_ID);
			// adding the query params to the URL
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			entityIds.stream().forEach(e -> uriBuilder.queryParam("entity_id", e));
			uriBuilder.queryParam("entity_type", entityType.name());
			entitySubTypeEnums.forEach(subType -> uriBuilder.queryParam("entity_sub_type",subType));
			privacyLevels.forEach(privacyLevel -> uriBuilder.queryParam("privacy_level",privacyLevel));
			log.info("Calling storage service to fetch for entity Id {} and entity type {} and entity_sub_types {}",
					entityIds,entityType,
					entitySubTypeEnums);
			getStoragesResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<ResponseDtoWrapper<List<StorageDto>>>() {});
			if (getStoragesResponse.getStatusCode().value() != 200) {
				throw new ServiceInvokeException("Error response recieved from storage service with error code "
						+ getStoragesResponse.getStatusCode().value());
			}

		} catch (Exception e) {
			log.error("Error invoking storage service {}", e);
			if (e instanceof ServiceInvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new ServiceInvokeException("Error invoking storage service");
			}
		}
		return getStoragesResponse.getBody().getData();
	}
	
	public void deleteStorage(String entityId) throws ServiceInvokeException, NotFoundException {
		ResponseEntity<Void> response;
		StringBuilder path = new StringBuilder();
		path.append(Constants.STORAGE_BASE_PATH).append(DELETE_BY_ID).append(entityId);
		// adding the query params to the URL
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
		log.info("Calling storage service to delete certificates for asset Id {}", entityId);
		response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.DELETE, null, Void.class);
		if (response.getStatusCode().value() != 200) {
			throw new ServiceInvokeException(
					"Error response recieved from storage service with error code " + response.getStatusCode().value());
		}

	}

}
