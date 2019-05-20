package com.reeyou.imall.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.dao.ProductDao;
import com.reeyou.imall.pojo.Product;
import com.reeyou.imall.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Reeyou
 * @data 2019/5/20 9:59
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;


	/**
	 * 添加、更新商品实现类
	 * @param product
	 * @return
	 */
	public ServerResponse saveOrUpdate(Product product) {
		//商品不为空值时
		if(product != null) {
			//判断商品图片-----设置商品信息主展示图
			if(StringUtils.isNotBlank(product.getSubImages())) {
				String[] subImageArray = product.getSubImages().split(",");
				if(subImageArray.length > 0) {
					product.setMainImage(subImageArray[0]);
				}
			}
			//根据id执行新增或者更新操作
			if(product.getId() == null) {
				int resultCount = productDao.insert(product);
				if(resultCount > 0) {
					return ServerResponse.serverSuccussMsg("新增商品信息成功");
				} else {
					return ServerResponse.serverErrorMsg("新增商品信息失败");
				}
			} else {
				int resultCount = productDao.updateByPrimaryKey(product);
				if(resultCount > 0) {
					return ServerResponse.serverSuccussMsg("更新商品信息成功");
				} else {
					return ServerResponse.serverErrorMsg("更新商品信息失败");
				}
			}
		}
		return ServerResponse.serverErrorMsg("新增或更新商品信息参数错误");
	}

	/**
	 * 商品上下架实现类
	 * @param productId
	 * @param status
	 * @return
	 */
	public ServerResponse setProductStatus(Integer productId, Integer status) {
		//参数错误
		if(productId == null && status == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.ERROR_AUGUMENTS.getCode(),ResponseEnums.ERROR_AUGUMENTS.getDesc());
		}
		Product product = new Product();
		product.setId(productId);
		product.setStatus(status);

		int resultCount = productDao.updateByPrimaryKeySelective(product);
		if(resultCount > 0) {
			return ServerResponse.serverSuccussMsg("修改商品状态成功");
		}
		return ServerResponse.serverErrorMsg("修改商品状态失败");
	}

	/**
	 * 获取商品列表实现类
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public ServerResponse getProductList(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return null;
	}

	/**
	 * 获取商品详情实现类
	 * @param productId
	 * @return
	 */
	public ServerResponse getProductDetail(Integer productId) {
		return null;
	}

	/**
	 * 商品搜索实现类
	 * @param productName
	 * @param productId
	 * @param pageNum
	 * @param pageSzie
	 * @return
	 */
	public ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSzie) {
		return null;
	}

}
