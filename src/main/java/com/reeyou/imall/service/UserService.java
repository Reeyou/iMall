package com.reeyou.imall.service;

import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * @author Reeyou
 * @data 2019/5/16 16:06
 */
public interface UserService {

	ServerResponse<User> login(String username, String password);

	ServerResponse<String> register(User user);

	ServerResponse<String> checkValid(String str, String type);

	ServerResponse<String> forgetResetPwd(String username, String email, String newPassword);

	ServerResponse<String> resetPwd(String password, String newPassword, HttpSession session);

	ServerResponse<User> getUserInfo(Integer userId);

	ServerResponse<User> updateUserInfo(User user);
}
