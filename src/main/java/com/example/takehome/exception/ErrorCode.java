package com.example.takehome.exception;

public enum ErrorCode {
	// Internal Errors: 1 to 0999
	GENERIC_ERROR("ERROR-0001", "The system is unable to complete the request. Contact system support."),
	JSON_PARSE_ERROR("ERROR-0002", "Make sure request payload should be a valid JSON object."),
	HTTP_REQUEST_METHOD_NOT_SUPPORTED("ERROR-0003", "Request method not supported."),
	ILLEGAL_ARGUMENT_EXCEPTION("ERROR-0008", "Invalid data passed."),
	RESOURCE_NOT_FOUND("ERROR-0010", "Requested resource not found.");

	private String errCode;
	private String errMsgKey;

	private ErrorCode(final String errCode, final String errMsgKey) {
		this.errCode = errCode;
		this.errMsgKey = errMsgKey;
	}

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @return the errMsgKey
	 */
	public String getErrMsgKey() {
		return errMsgKey;
	}
}
