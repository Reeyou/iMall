package com.reeyou.imall.controller.admin;

import com.reeyou.imall.common.Constant;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.Product;
import com.reeyou.imall.pojo.User;
import com.reeyou.imall.service.ProductService;
import com.reeyou.imall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	public ServerResponse addOrUpdateProduct(HttpSession session, Product product) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
		if(userService.checkRole(user.getRole()).isSuccuss()) {
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
	public ServerResponse setProductStatus(HttpSession session, Integer productId, Integer status) {
		User user = (User)session.getAttribute(Constant.CURRENT_USER);
//		if(user == null) {
//			return ServerResponse.serverErrorMsg("请登录")
//		}
		if(userService.checkRole(user.getRole()).isSuccuss()) {
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
	public ServerResponse getProductList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
										 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		return null;
	}

	/**
	 * 获取商品详情
	 * @param session
	 * @param productId
	 * @return
	 */
	public ServerResponse getProductDetail(HttpSession session, Integer productId) {
		return null;
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
	public ServerResponse searchProduct(HttpSession session, String productName, Integer productId,
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
	 * 上传文本文件
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
