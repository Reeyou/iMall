package com.reeyou.imall.controller.web;

import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Reeyou
 * @data 2019/5/20 9:58
 */
@RestController
@RequestMapping("/admin/product/")
@Api(value = "webProduct", tags = {"web商品管理"})
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * web获取商品列表
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @param categoryId
	 * @param keyWord
	 * @return
	 */
	@GetMapping("/getProductList")
	@ResponseBody
	@ApiOperation(value = "获取商品列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryId",value = "商品id",paramType = "query",dataType = "String",required = true),
			@ApiImplicitParam(name = "pageNum",value = "页数",paramType = "query",dataType = "int",required = true),
			@ApiImplicitParam(name = "pageSize",value = "每页条数",paramType = "query",dataType = "int",required = true),
			@ApiImplicitParam(name = "keyWord",value = "商品关键词",paramType = "query",dataType = "String",required = false),
			@ApiImplicitParam(name = "orderBy",value = "排序",paramType = "query",dataType = "String",required = false)
	})
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
	@ApiOperation(value = "获取商品详情")
	@ApiImplicitParam(name = "categoryId",value = "商品id",paramType = "query",dataType = "Integer",required = true)
	public ServerResponse getProductDetail(Integer categoryId) {

		return productService.getProductDetail(categoryId);
	}


}
