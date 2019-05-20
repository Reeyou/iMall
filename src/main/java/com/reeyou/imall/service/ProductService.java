package com.reeyou.imall.service;

import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.Product;

/**
 * @author Reeyou
 * @data 2019/5/20 9:59
 */
public interface ProductService {

	ServerResponse saveOrUpdate(Product product);

	ServerResponse setProductStatus(Integer productId, Integer status);

	ServerResponse getProductList(int pageNum, int pageSize);

	ServerResponse getProductDetail(Integer productId);

	ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSzie);
}
