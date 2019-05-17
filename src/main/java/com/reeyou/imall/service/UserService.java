package com.reeyou.imall.service;

import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;

/**
 * @author Reeyou
 * @data 2019/5/16 16:06
 */
public interface UserService {

	ServerResponse<User> login(String username, String password);

	ServerResponse<String> register(User user);

	ServerResponse<String> checkValid(String str, String type);

	ServerResponse<String> resetPwd(String username);
}
