package com.reeyou.imall.controller;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> login(String username, String password, HttpSession session) {

		//	service => mybatis => dao

		ServerResponse<User> response = UserService.login(username, password);
		if(response.isSuccuss()) {
			session.setAttribute(Constant.CURRENT_USER, response.getData());
		}
		return response;
	}

	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> loginOut(HttpSession session) {
		session.removeAttribute(Constant.CURRENT_USER);
		return ServerResponse.serverSuccuss();
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> register(User user) {
		return UserService.register(user);
	}

	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<User> getUserInfo(HttpSession session) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user != null) {
			return ServerResponse.serverSuccuss(user);
		}
		return ServerResponse.serverErrorMsg("用户未登录，无法获取用户信息！");
	}

	@RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> resetPwd(String username) {
		return UserService.resetPwd(username);
	}

}
