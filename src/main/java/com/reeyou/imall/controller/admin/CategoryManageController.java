package com.reeyou.imall.controller.admin;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.CategoryService;
import com.reeyou.imall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Reeyou
 * @data 2019/5/18 20:48
 */
@RestController
@RequestMapping("/admin/")
public class CategoryManageController {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 添加品类
	 * @param session
	 * @param categoryName
	 * @param parentId
	 * @return
	 */
	@GetMapping(value = "/addCategory")
	@ResponseBody
	public ServerResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorMsg("当前用户未登录");
		}
		//检查是否为管理员
		if(userService.checkRole(user).isSuccuss()) {
			return categoryService.addCategory(categoryName,parentId);
		} else {
			return ServerResponse.serverSuccussMsg("无操作权限");
		}
	}

	/**
	 * 更新品类名称
	 * @param session
	 * @param categoryName
	 * @param categoryId
	 * @return
	 */
	@GetMapping(value = "/updateCategory")
	@ResponseBody
	public ServerResponse<String> updateCategory(HttpSession session, String categoryName, Integer categoryId) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorMsg("当前用户未登录");
		}
		//检查是否为管理员
		if(userService.checkRole(user).isSuccuss()) {
			return categoryService.updateCategory(categoryName,categoryId);
		} else {
			return ServerResponse.serverSuccussMsg("无操作权限");
		}

	}

	/**
	 * 获取总品类
	 * @param session
	 * @param categoryId
	 * @return
	 */
	@GetMapping(value = "/getCategoryList")
	@ResponseBody
	public ServerResponse getCategoryList(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorMsg("当前用户未登录");
		}
		//检查是否为管理员
		if(userService.checkRole(user).isSuccuss()) {
			return categoryService.findCategoryList(categoryId);
		} else {
			return ServerResponse.serverSuccussMsg("无操作权限");
		}
	}

	/**
	 * 获取品类子节点列表
	 * @param session
	 * @param categoryId
	 * @return
	 */
	@GetMapping(value = "/getCategoryChildrenList")
	@ResponseBody
	public ServerResponse getCategoryChildrenList(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorMsg("当前用户未登录");
		}
		//检查是否为管理员
		if(userService.checkRole(user).isSuccuss()) {
			return categoryService.findCategoryChildrenList(categoryId);
		} else {
			return ServerResponse.serverSuccussMsg("无操作权限");
		}
	}

}
