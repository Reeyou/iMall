package com.reeyou.imall.service.Impl;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.common.TokenCache;
import com.reeyou.imall.dao.UserDao;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.UserService;
import com.reeyou.imall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author Reeyou
 * @data 2019/5/16 16:08
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public ServerResponse<User> login(String username, String password) {

		int resultCount = userDao.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("用户名不存在！");
		}

		String md5Password = MD5Util.MD5EncodeUtf8(password);

		User user = userDao.matchUser(username, md5Password);
		if(user == null) {
			return ServerResponse.serverErrorMsg("密码错误！");
		}

		user.setPassword(StringUtils.EMPTY);
		String userToken = UUID.randomUUID().toString();
		System.out.println("token:" + userToken);
		TokenCache.setToken(TokenCache.TOKEN+username,userToken);
		return ServerResponse.serverSuccuss("登录成功",user);
	}

	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@Override
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
		int resultCount = userDao.insert(user);
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
	@Override
	public ServerResponse<String> checkValid(String str, String type) {
		if(StringUtils.isNoneBlank(type)) {
			if(Constant.USERNAME.equals(type)) {
				int resultCount = userDao.checkUsername(str);
				if(resultCount > 0) {
					return ServerResponse.serverErrorMsg("用户名已存在！");
				}
			}
			if(Constant.EMAIL.equals(type)) {
				int resultCount = userDao.checkEmail(str);
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
	 * 忘记密码
	 * @param username
	 * @param email
	 * @param newPassword
	 * @return
	 */
	@Override
	public ServerResponse<String> forgetResetPwd(String username, String email, String newPassword) {
		int resultCount = userDao.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("用户不存在!");
		}
		resultCount = userDao.checkEmail(email);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("邮箱不存在！");
		}
		resultCount = userDao.checkEmailByUsername(email, username);
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("邮箱不匹配！");
		}

		String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
		int updateCount = userDao.updatePwdByUsername(username, md5Password);
		if(updateCount > 0) {
			return ServerResponse.serverSuccussMsg("密码修改成功！");
		}
		return ServerResponse.serverErrorMsg("密码修改失败！");
	}
	/**
	 * 重置密码
	 * @param password
	 * @param newPassword
	 * @param session
	 * @return
	 */
	@Override
	public ServerResponse<String> resetPwd(String password, String newPassword, HttpSession session) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);

		String md5Password = MD5Util.MD5EncodeUtf8(password);
		String md5NewPassword = MD5Util.MD5EncodeUtf8(newPassword);
		int resultCount = userDao.checkPasswordByUsername(md5Password, user.getUsername());
		if(resultCount == 0) {
			return ServerResponse.serverErrorMsg("原密码错误！");
		}

		if(user != null) {
			resultCount = userDao.updatePwdByUsername(user.getUsername(), md5NewPassword);
			if(resultCount > 0) {
				return ServerResponse.serverSuccussMsg("修改密码成功！");
			}
		} else {
			return ServerResponse.serverErrorMsg("当前用户未登录");
		}
		return ServerResponse.serverErrorMsg("修改密码失败！");
	}

	/**
	 * 获取个人信息
	 * @param userId
	 * @return
	 */
	@Override
	public ServerResponse<User> getUserInfo(Integer userId) {
		User user = userDao.selectByPrimaryKey(userId);
		if(user == null) {
			return ServerResponse.serverErrorMsg("找不到当前用户！");
		}
		user.setPassword(StringUtils.EMPTY);
		return ServerResponse.serverSuccuss(user);
	}

	/**
	 * 更新个人信息
	 * @param user
	 * @return
	 */
	@Override
	public ServerResponse<User> updateUserInfo(User user) {
		int resultCount = userDao.checkEmailByUsername(user.getEmail(), user.getUsername());
		if(resultCount > 0) {
			return ServerResponse.serverErrorMsg("email地址已存在！");
		}

		resultCount = userDao.checkUsername(user.getUsername());
		if(resultCount > 0) {
			return ServerResponse.serverErrorMsg("用户名已存在!");
		}
		User updateUser = new User();
		updateUser.setId(user.getId());
//		System.out.println("userId:" + updateUser.getId());
		updateUser.setUsername(user.getUsername());
//		System.out.println("updateUsername:" + updateUser.getUsername());
		updateUser.setEmail(user.getEmail());
		updateUser.setPhone(user.getPhone());

//		todo 是不是无法根据id更新userInfo
		int updateCount = userDao.updateByPrimaryKeySelective(updateUser);
		System.out.println("updateCount:" + updateCount);
		if(updateCount > 0) {
			return ServerResponse.serverSuccuss("个人信息更改成功！", updateUser);
		}
		return ServerResponse.serverErrorMsg("个人信息更改失败！");
	}

	/**
	 * 管理端 检查角色
	 * @param user
	 * @return
	 */
	@Override
	public ServerResponse checkRole(User user) {
		if(user != null && user.getRole().intValue() == Constant.Role.ROLE_ADMIN) {
			return ServerResponse.serverSuccuss();
		}
		return ServerResponse.serverError();
	}


}
