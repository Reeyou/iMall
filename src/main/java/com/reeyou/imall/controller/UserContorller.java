package com.reeyou.imall.controller;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Reeyou
 * @data 2019/5/16 15:59
 */

@RestController
@RequestMapping("/user/")
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
	@PostMapping(value = "/login")
	@ResponseBody
	public ServerResponse<User> login(String username, String password, HttpSession session) {

		//	service => mybatis => dao

		ServerResponse<User> response = UserService.login(username, password);
		if(response.isSuccuss()) {
			session.setAttribute(Constant.CURRENT_USER, response.getData());
		}
		return response;
	}

	@GetMapping(value = "/loginOut")
	@ResponseBody
	public ServerResponse<String> loginOut(HttpSession session) {
		session.removeAttribute(Constant.CURRENT_USER);
		return ServerResponse.serverSuccuss();
	}

	@PostMapping(value = "/register")
	@ResponseBody
	public ServerResponse<String> register(User user) {
		return UserService.register(user);
	}


	@GetMapping(value = "/forgetPwd")
	@ResponseBody
	public ServerResponse<String> forgetPwd(String username, String email, String newPassword) {
		return UserService.forgetResetPwd(username, email, newPassword);
	}

	@GetMapping(value = "/resetPwd")
	@ResponseBody
	public ServerResponse<String> resetPwd(String username, String password, String newPassword, String userToken) {
		return UserService.resetPwd(username, password, newPassword, userToken);
	}

	@GetMapping(value = "/getUserInfo")
	@ResponseBody
	public ServerResponse<User> getUserInfo(HttpSession session) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(), "当前用户未登录！");
		}
		return UserService.getUserInfo(user.getId());
	}

	@PostMapping(value = "/updateUserInfo")
	@ResponseBody
	public ServerResponse<User> updateUserInfo(HttpSession session, User user) {
		User currentUser = (User)session.getAttribute(Constant.CURRENT_USER);
		if(currentUser == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(), "当前用户未登录！");
		}
		user.setId(currentUser.getId());
//		user.setUsername(currentUser.getUsername());

		ServerResponse<User> response = UserService.updateUserInfo(user);
		if(response.isSuccuss()) {
			response.getData().setUsername(currentUser.getUsername());
			session.setAttribute(Constant.CURRENT_USER, response.getData());
		}
		return response;
	}

}
