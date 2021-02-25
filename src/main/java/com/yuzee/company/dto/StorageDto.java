package com.yuzee.company.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;

import lombok.Data;

@Data
public class StorageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4367433035513568299L;

	@JsonProperty("entity_id")
	private String entityId;

	@JsonProperty("entity_type")
	private EntityTypeEnum entityType;

	@JsonProperty("entity_sub_type")
	private EntitySubTypeEnum entitySubType;

	@JsonProperty("stored_file_name")
	private String storedFileName;

	@JsonProperty("file_type")
	private String fileType;

	@JsonProperty("original_file_name")
	private String originalFileName;

	@JsonProperty("base_url")
	private String baseUrl;

	@JsonProperty("file_url")
	private String fileURL;

	@JsonProperty("thumbnails")
	private List<ThumbnailsDTO> thumbnails;
}
