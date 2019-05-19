package com.reeyou.imall.controller.admin;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.dao.UserDao;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.UserService;
import com.reeyou.imall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Reeyou
 * @data 2019/5/18 10:15
 */
@RestController
@RequestMapping("/admin/")
public class UserAdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@PostMapping(value = "/login")
	@ResponseBody
	public ServerResponse<User> adminLogin(String username, String password, HttpSession session) {
		ServerResponse<User> response = userService.login(username, password);
		if(response.isSuccuss()) {
			User user = response.getData();

			if(user.getRole() == Constant.Role.ROLE_ADMIN) {
				session.setAttribute(Constant.CURRENT_USER, user);

				return response;
			} else {
				return ServerResponse.serverErrorMsg("不是管理员，无法登录！");
			}
		}
		return response;
	}
	@GetMapping(value = "/getUserInfo")
	@ResponseBody
	public ServerResponse<User> adminGetUserInfo(HttpSession session) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);

		if(user == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(), "当前用户未登录！");
		}
		return userService.getUserInfo(user.getId());
	}

	@PostMapping(value = "/resetPwd")
	@ResponseBody
	public ServerResponse<User> adminResetPwd(String password, String newPassword, HttpSession session) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		int resultCount = userDao.checkPasswordByUsername(md5Password, user.getUsername());
		if(resultCount > 0) {
			String md5NewPassword = MD5Util.MD5EncodeUtf8(newPassword);
			userDao.updatePwdByUsername(user.getUsername(), md5NewPassword);
			user.setPassword(StringUtils.EMPTY);
			return ServerResponse.serverSuccuss("修改密码成功！", user);
		}
		return ServerResponse.serverErrorMsg("原密码错误！");
	}


}
