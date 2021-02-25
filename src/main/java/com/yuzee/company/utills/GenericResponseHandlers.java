package com.yuzee.company.utills;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class GenericResponseHandlers {

	private static final String DATA_CONST = "data";
	private static final String STATUS_CODE_CONST = "status";
	private static final String MESSAGE_CONST = "message";
	private final Object data;
	private final StatusType status;
	private final String message;

	private GenericResponseHandlers(final Builder builder) {
		this.data = builder.data;
		this.message = builder.message;
		this.status = builder.status;
	}

	public static class Builder {
		private Object data;
		private StatusType status;
		private String message;

		public Builder setData(final Object data) {
			this.data = data;
			return this;
		}

		public Builder setStatus(final StatusType status) {
			this.status = status;
			return this;
		}

		public Builder setMessage(final String message) {
			this.message = message;
			return this;
		}

		public Response create() {
			final GenericResponseHandlers handler = new GenericResponseHandlers(this);
			final Map<String, Object> responseMap = new HashMap<>(4);
			responseMap.put(STATUS_CODE_CONST, handler.status.getStatusCode());
			responseMap.put(MESSAGE_CONST, handler.message);
			if (handler.data != null) {
				responseMap.put(DATA_CONST, handler.data);
			}

			return Response.status(handler.status).entity(responseMap).build();
		}

	}

	public static Object Builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
