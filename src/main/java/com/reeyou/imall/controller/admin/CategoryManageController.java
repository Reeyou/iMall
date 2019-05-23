package com.reeyou.imall.controller.admin;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.CategoryService;
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
 * @data 2019/5/18 20:48
 */
@RestController
@RequestMapping("/admin/")
@Api(value = "adminCategory", tags = {"admin品类管理"})
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
	@PostMapping(value = "/addCategory")
	@ResponseBody
	@ApiOperation(value = "添加品类信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "categoryName",value = "品类名称",paramType = "query",dataType = "String",required = true),
		@ApiImplicitParam(name = "parentId",value = "品类id",paramType = "query",dataType = "int",required = true),
	})
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
	@PostMapping(value = "/updateCategory")
	@ResponseBody
	@ApiOperation(value = "更新品类名称")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "categoryName",value = "品类名称",paramType = "query",dataType = "String",required = true),
		@ApiImplicitParam(name = "categoryId",value = "品类id",paramType = "query",dataType = "int",required = true),
	})
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
	@ApiOperation(value = "获取总品类列表")
	@ApiImplicitParam(name = "categoryId",value = "商品id",paramType = "query",dataType = "int",required = true)
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
	 * @param parentId
	 * @return
	 */
	@ApiOperation(value = "获取总品类子品类列表")
	@PostMapping(value = "/getCategoryChildrenList")
	@ResponseBody
	@ApiImplicitParam(name = "parentId",value = "商品根节点id",paramType = "query",dataType = "int",required = true)
	public ServerResponse getCategoryChildrenList(HttpSession session, Integer parentId) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorMsg("当前用户未登录");
		}
		if(parentId == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.ERROR_AUGUMENTS.getCode(),"参数错误");
		}
		//检查是否为管理员
		if(userService.checkRole(user).isSuccuss()) {
			return categoryService.findCategoryChildrenList(parentId);
		} else {
			return ServerResponse.serverSuccussMsg("无操作权限");
		}
	}

}
