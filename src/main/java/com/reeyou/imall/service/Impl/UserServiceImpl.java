package com.reeyou.imall.service.Impl;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.common.TokenCache;
import com.reeyou.imall.dao.UserMapper;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.UserService;
import com.reeyou.imall.utils.MD5Util;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Reeyou
 * @data 2019/5/16 16:08
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public ServerResponse<User> login(String username, String password) {

		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("用户名不存在！");
		}

		String md5Password = MD5Util.MD5EncodeUtf8(password);

		User user = userMapper.matchUser(username, md5Password);
		if(user == null) {
			return ServerResponse.serverErrorMsg("密码错误！");
		}

		user.setPassword(StringUtils.EMPTY);
		String userToken = UUID.randomUUID().toString();
		TokenCache.setToken(TokenCache.TOKEN+username,userToken);
		return ServerResponse.serverSuccuss("登录成功",user);
	}

	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public ServerResponse<String> register(User user) {

		ServerResponse validResponse = this.checkValid(user.getUsername(), Constant.USERNAME);
		if(!validResponse.isSuccuss()) {
			return validResponse;
		}

		validResponse = this.checkValid(user.getEmail(), Constant.EMAIL);
		if(!validResponse.isSuccuss()) {
			return validResponse;
		}

		user.setRole(Constant.Role.ROLE_CUSTOMER);
		//MD55加密
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		int resultCount = userMapper.insert(user);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("注册失败");
		}
		return ServerResponse.serverSuccussMsg("注册成功");
	}

	/**
	 * 检验用户
	 * @param str
	 * @param type
	 * @return
	 */
	public ServerResponse<String> checkValid(String str, String type) {
		if(StringUtils.isNoneBlank(type)) {
			if(Constant.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUsername(str);
				if(resultCount > 0) {
					return ServerResponse.serverErrorMsg("用户名已存在！");
				}
			}
			if(Constant.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if(resultCount > 0) {
					return ServerResponse.serverErrorMsg("邮箱已存在！");
				}
			}
		} else {
			return ServerResponse.serverErrorMsg("参数错误");
		}
		return ServerResponse.serverSuccussMsg("校验成功");
	}

	/**
	 * 重置密码
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param userToken
	 * @return
	 */
	public ServerResponse<String> resetPwd(String username, String password, String newPassword, String userToken) {
		if(StringUtils.isBlank(userToken)) {
			return ServerResponse.serverErrorMsg("参数错误，缺少Token!");
		}
		ServerResponse validUsername = this.checkValid(username, Constant.USERNAME);
		if(validUsername.isSuccuss()) {
			return ServerResponse.serverErrorMsg("用户不存在！");
		}
		int resultCount = userMapper.checkPassword(password);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("原密码错误！");
		}

		String token = TokenCache.getToken(TokenCache.TOKEN+username);
		if(StringUtils.isBlank(token)) {
			return ServerResponse.serverErrorMsg("Token无效或过期！");
		}

		if(StringUtils.equals(token, userToken)) {
			String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
			resultCount = userMapper.updatePwdByUsername(username, md5Password);
			if(resultCount > 0) {
				return ServerResponse.serverSuccussMsg("更新密码成功！");
			}
		} else {
			return ServerResponse.serverErrorMsg("Token错误！请重新获取Token");
		}
		return ServerResponse.serverErrorMsg("更新密码失败！");
	}

	/**
	 * 忘记密码
	 * @param username
	 * @param email
	 * @param newPassword
	 * @return
	 */
	public ServerResponse<String> forgetResetPwd(String username, String email, String newPassword) {
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("用户不存在!");
		}
		resultCount = userMapper.checkEmail(email);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("邮箱不存在！");
		}
		resultCount = userMapper.checkEmailByUsername(email, username);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("邮箱不匹配！");
		}

		String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
		int updateCount = userMapper.updatePwdByUsername(username, md5Password);
		if(updateCount > 0) {
			return ServerResponse.serverSuccussMsg("密码修改成功！");
		}
		return ServerResponse.serverErrorMsg("密码修改失败！");
	}
}
