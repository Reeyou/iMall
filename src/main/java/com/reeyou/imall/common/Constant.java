package com.reeyou.imall.common;

import com.google.common.collect.Sets;

import java.util.Set;

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

	public interface ProductListOrderBy{
		Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
	}

	public enum ProductStatusEnum{
		ON_SALE(1,"在线");
		private String value;
		private int code;
		ProductStatusEnum(int code,String value){
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}
	}

	public interface Cart{
		int CHECKED = 1;//即购物车选中状态
		int UN_CHECKED = 0;//购物车中未选中状态

		String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
		String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
	}

}
