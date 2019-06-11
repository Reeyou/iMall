package com.reeyou.imall.common;

/**
 * @author Reeyou
 * @data 2019/5/16 16:17
 */
public enum ResponseEnums {

	SUCCUSS(200, "SUCCUSS"),
	ERROR(1, "ERROR"),
	UNLOGIN(10, "UNLOGIN"),
	ERROR_AUGUMENTS(2, "ERROR_ARGUMENTS");

	private final int code;
	private final String desc;

	ResponseEnums(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return  code;
	}

	public String getDesc() {
		return desc;
	}
}
