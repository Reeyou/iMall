package com.reeyou.imall.controller.web;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Reeyou
 * @data 2019/5/16 15:59
 */

@RestController
@RequestMapping("/user/")
@Api(value = "webProduct", tags = {"web用户管理"})
public class UserContorller {

	@Autowired
	private UserService UserService;

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@ApiOperation(value = "用户登录")
	@PostMapping(value = "/login")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username",value = "用户名",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "password",value = "用户密码",paramType = "query",dataType = "String",required = true)
	})
	public ServerResponse<User> login(String username, String password, HttpSession session) {

		//	service => mybatis => dao

		ServerResponse<User> response = UserService.login(username, password);
		if(response.isSuccuss()) {
			session.setAttribute(Constant.CURRENT_USER, response.getData());
			System.out.println("session:" + session.getAttribute(Constant.CURRENT_USER));
		}
		return response;
	}

	@ApiOperation(value = "用户注销")
	@GetMapping(value = "/loginOut")
	@ResponseBody
	public ServerResponse<String> loginOut(HttpSession session) {
		session.removeAttribute(Constant.CURRENT_USER);
		return ServerResponse.serverSuccuss();
	}

	@ApiOperation(value = "用户注册")
	@PostMapping(value = "/register")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username",value = "用户名",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "password",value = "用户密码",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "email",value = "用户邮箱",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "phone",value = "用户手机号",paramType = "query",dataType = "String",required = true)
	})
	public ServerResponse<String> register(User user) {
		return UserService.register(user);
	}


	@ApiOperation(value = "忘记密码")
	@GetMapping(value = "/forgetPwd")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username",value = "用户名",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "password",value = "用户密码",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "email",value = "用户邮箱",paramType = "query",dataType = "String",required = true)
	})
	public ServerResponse<String> forgetPwd(String username, String email, String newPassword) {
		return UserService.forgetResetPwd(username, email, newPassword);
	}

	@ApiOperation(value = "登录状态 重置密码")
	@GetMapping(value = "/resetPwd")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "password",value = "用户原密码",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "newPassword",value = "用户新密码",paramType = "query",dataType = "String",required = true)
	})
	public ServerResponse<String> resetPwd(String password, String newPassword, HttpSession session) {
		return UserService.resetPwd(password, newPassword, session);
	}

	@ApiOperation(value = "获取用户信息")
	@GetMapping(value = "/getUserInfo")
	@ResponseBody
	public ServerResponse<User> getUserInfo(HttpSession session) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(), "当前用户未登录！");
		}
		return UserService.getUserInfo(user.getId());
	}

	@ApiOperation(value = "更新用户信息")
	@PostMapping(value = "/updateUserInfo")
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username",value = "用户名",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "password",value = "用户密码",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "email",value = "用户邮箱",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "phone",value = "用户手机号",paramType = "query",dataType = "String",required = true)
	})
	public ServerResponse<User> updateUserInfo(HttpSession session, User user) {
		User currentUser = (User)session.getAttribute(Constant.CURRENT_USER);
		if(currentUser == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(), "当前用户未登录！");
		}
		System.out.println("currentId:" + currentUser.getId());
		user.setId(currentUser.getId());
//		user.setUsername(currentUser.getUsername());

		ServerResponse<User> response = UserService.updateUserInfo(user);
		if(response.isSuccuss()) {
//			response.getData().setUsername(currentUser.getUsername());
			session.setAttribute(Constant.CURRENT_USER, response.getData());
		}
		return response;
	}

}
