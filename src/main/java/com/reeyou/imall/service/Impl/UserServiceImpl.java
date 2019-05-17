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

//		int resultCount = userMapper.checkUsername(userName);
//		if(resultCount == 0) {
//			return ServerResponse.serverErrorMsg("用户名不存在！");
//		}
//		todo 密码登录MD5
		String md5Password = MD5Util.MD5EncodeUtf8(password);

		User user = userMapper.selectLogin(username, md5Password);
		if(user == null) {
			return ServerResponse.serverErrorMsg("密码错误！");
		}

		user.setPassword(StringUtils.EMPTY);
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

	public ServerResponse<String> resetPwd(String username) {
		ServerResponse validResponse = this.checkValid(username, Constant.USERNAME);
		if(validResponse.isSuccuss()) {
			return ServerResponse.serverErrorMsg("用户不存在！");
		}
		String question = userMapper.selectQuestionByUsername(username);
		if(StringUtils.isNoneBlank(question)) {
			return ServerResponse.serverSuccuss(question);
		}
		return ServerResponse.serverErrorMsg("找回密码问题错误");
	}

//	public ServerResponse<String> checkPwd(String password) {
//		String token = UUID.randomUUID().toString();
//		TokenCache.setKey("token_", token);
//	}
}
