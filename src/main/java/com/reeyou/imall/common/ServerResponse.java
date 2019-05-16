package com.reeyou.imall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author Reeyou
 * @data 2019/5/16 16:10
 */
//保证序列化json的时候，如果是null对象，那么key也会消失
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

	private int status;
	private String msg;
	private T data;

	private ServerResponse(int status) {
		this.status = status;
	}
	private ServerResponse(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	private ServerResponse(int status, T data) {
		this.status = status;
		this.data = data;
	}
	private ServerResponse(int status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	//	公共方法
	//使之不在序列化对象结果当中，因为is开头会被定义为常量
	@JsonIgnore
	public boolean isSuccuss() {
		return this.status == ResponseEnums.SUCCUSS.getCode();
	}

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}
	//	请求成功
	public static <T> ServerResponse<T> serverSuccuss() {
		return new ServerResponse<T>(ResponseEnums.SUCCUSS.getCode());
	}

	public static <T> ServerResponse<T> serverSuccussMsg(String msg) {
		return new ServerResponse<T>(ResponseEnums.SUCCUSS.getCode(), msg);
	}

	public static <T> ServerResponse<T> serverSuccuss(T data) {
		return new ServerResponse<T>(ResponseEnums.SUCCUSS.getCode(), data);
	}

	public static <T> ServerResponse<T> serverSuccuss(String msg, T data) {
		return new ServerResponse<T>(ResponseEnums.SUCCUSS.getCode(), msg, data);
	}

	//	请求错误
	public static <T> ServerResponse<T> serverError() {
		return new ServerResponse<T>(ResponseEnums.ERROR.getCode(), ResponseEnums.ERROR.getDesc());
	}

	//	返回错误信息
	public static <T> ServerResponse<T> serverErrorMsg(String errorMsg) {
		return new ServerResponse<T>(ResponseEnums.ERROR.getCode(), errorMsg);
	}

	//	返回错误码和信息
	public static <T> ServerResponse<T> serverErrorCodeMsg(int errorCode,String errorMsg) {
		return new ServerResponse<T>(errorCode, errorMsg);
	}

}
