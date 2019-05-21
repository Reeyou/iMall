package com.reeyou.imall.controller.admin;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.Product;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.ProductService;
import com.reeyou.imall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Reeyou
 * @data 2019/5/20 9:58
 */
@RestController
@RequestMapping("/admin/")
@Api(value = "adminProduct", tags = {"admin商品管理"})
public class ProductManageController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;



	/**
	 * 添加、更新商品
	 * @param session
	 * @param product
	 * @return
	 */
	@PostMapping("/addOrUpdateProduct")
	@ResponseBody
	@ApiOperation(value = "添加或更新商品信息")
	public ServerResponse addOrUpdateProduct(HttpSession session, Product product) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(userService.checkRole(user).isSuccuss()) {
			return productService.saveOrUpdate(product);
		} else {
			return ServerResponse.serverErrorMsg("无操作权限");
		}
	}

	/**
	 * 商品上下架
	 * @param session
	 * @param productId
	 * @param status
	 * @return
	 */
	@PostMapping("/updateProductStatus")
	@ResponseBody
	@ApiOperation(value = "修改商品上下架状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productId",value = "商品id",paramType = "query",dataType = "int",required = true),
			@ApiImplicitParam(name = "status",value = "状态",paramType = "query",dataType = "int",required = true)
	})
	public ServerResponse setProductStatus(HttpSession session, Integer productId, Integer status) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
//		if(user == null) {
//			return ServerResponse.serverErrorMsg("请登录")
//		}
		if(userService.checkRole(user).isSuccuss()) {
			return productService.setProductStatus(productId,status);
		} else {
			return ServerResponse.serverErrorMsg("无操作权限");
		}
	}

	/**
	 * 获取商品列表
	 * @param session
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/getProductList")
	@ResponseBody
	@ApiOperation(value = "获取商品列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum",value = "页数",paramType = "query",dataType = "int",required = true),
			@ApiImplicitParam(name = "pageSize",value = "每页条数",paramType = "query",dataType = "int",required = true)
	})
	public ServerResponse getProductList(HttpSession session,
										 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
										 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(),"用户未登录");
		}
		if(userService.checkRole(user).isSuccuss()) {
			return productService.getProductList(pageNum, pageSize);
		} else {
			return ServerResponse.serverErrorMsg("无权限操作");
		}
	}

	/**
	 * 获取商品详情
	 * @param productId
	 * @param session
	 * @return
	 */
	@PostMapping("/getProductDetail")
	@ResponseBody
	@ApiOperation(value = "获取商品详情")
	@ApiImplicitParam(name = "productId",value = "商品id",paramType = "query",dataType = "int",required = true)
	public ServerResponse getProductDetail(HttpSession session, Integer productId) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.UNLOGIN.getCode(),"用户未登录");
		}
		if(userService.checkRole(user).isSuccuss()) {
			return productService.getProductDetail(productId);
		} else {
			return ServerResponse.serverErrorMsg("无权限操作");
		}

	}



	/**
	 * 商品搜索
	 * @param session
	 * @param productName
	 * @param productId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/searchProduct")
	@ResponseBody
	@ApiOperation(value = "搜索商品")
	public ServerResponse searchProduct(HttpSession session,
										String productName,
										Integer productId,
										@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
										@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		return null;
	}

	/**
	 * 上传图片
	 * @param session
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	public ServerResponse uploadImg(HttpSession session,
									@RequestParam(value="upload_file", required = false) MultipartFile file,
									HttpServletRequest request,
									HttpServletResponse response) {
		return null;

	}

	/**
	 * 上传富文本中图片
	 * @param session
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	public ServerResponse uploadTextFile(HttpSession session,
									@RequestParam(value="upload_file", required = false) MultipartFile file,
									HttpServletRequest request,
									HttpServletResponse response) {
		return null;

	}


}
