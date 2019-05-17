package com.reeyou.imall.common;

/**
 * @author Reeyou
 * @data 2019/5/16 17:41
 */
public class Constant {

	public static final String CURRENT_USER = "currentUser";

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";

	public interface  Role {
		int ROLE_CUSTOMER = 0; // 用户
		int ROLE_ADMIN = 1; // 管理员
	}

}
