package com.reeyou.imall.controller.web;

import com.reeyou.imall.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author Reeyou
 * @data 2019/5/20 9:58
 */
@RestController
@RequestMapping("/admin/product/")
public class ProductController {

	/**
	 * web获取商品列表
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @param categoryId
	 * @param keyWord
	 * @return
	 */
	@GetMapping("/getProduct")
	@ResponseBody
	public ServerResponse getProductList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
										 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
										 @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
										 @RequestParam(value = "categoryId", required = false) Integer categoryId,
										 @RequestParam(value = "keyWord", required = false) String keyWord) {
		return null;
	}

	/**
	 * web获取该商品详情
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/getProductDetail")
	@ResponseBody
	public ServerResponse getProductDetail(Integer categoryId) {
		return null;
	}


}
