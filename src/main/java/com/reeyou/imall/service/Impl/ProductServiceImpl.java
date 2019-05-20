package com.reeyou.imall.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.dao.CategoryDao;
import com.reeyou.imall.dao.ProductDao;
import com.reeyou.imall.pojo.Category;
import com.reeyou.imall.pojo.Product;
import com.reeyou.imall.service.ProductService;
import com.reeyou.imall.utils.DateTimeUtil;
import com.reeyou.imall.utils.PropertiesUtil;
import com.reeyou.imall.vo.ProductDetailVo;
import com.reeyou.imall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Reeyou
 * @data 2019/5/20 9:59
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;


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
	public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Product> productList = productDao.selectProductList();

		List<ProductListVo> productListVoList = Lists.newArrayList();

		for(Product productItem : productList) {
			ProductListVo productListVo = addProductListVo(productItem);
			productListVoList.add(productListVo);
		}
		//用PageInfo对结果进行包装
		PageInfo pageResult = new PageInfo(productList);
		pageResult.setList(productListVoList);
		return ServerResponse.serverSuccuss(pageResult);
	}

	private ProductListVo addProductListVo(Product product){
		ProductListVo productListVo = new ProductListVo();
		productListVo.setId(product.getId());
		productListVo.setName(product.getName());
		productListVo.setCategoryId(product.getCategoryId());
		productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
		productListVo.setMainImage(product.getMainImage());
		productListVo.setPrice(product.getPrice());
		productListVo.setSubtitle(product.getSubtitle());
		productListVo.setStatus(product.getStatus());
		return productListVo;
	}

	/**
	 * 获取商品详情实现类
	 * @param productId
	 * @return
	 */
	public ServerResponse getProductDetail(Integer productId) {
		if(productId == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.ERROR_AUGUMENTS.getCode(),ResponseEnums.ERROR_AUGUMENTS.getDesc());
		}

		Product product = productDao.selectByPrimaryKey(productId);
		if(product == null) {
			return ServerResponse.serverErrorMsg("该商品已下架或删除");
		}
		//返回productDetailVo
		ProductDetailVo productDetailVo = addProductDetailVo(product);
		return ServerResponse.serverSuccuss(productDetailVo);
	}

	private ProductDetailVo addProductDetailVo(Product product) {
		ProductDetailVo productDetailVo = new ProductDetailVo();
		productDetailVo.setId(product.getId());
		productDetailVo.setSubtitle(product.getSubtitle());
		productDetailVo.setPrice(product.getPrice());
		productDetailVo.setMainImage(product.getMainImage());
		productDetailVo.setSubImages(product.getSubImages());
		productDetailVo.setCategoryId(product.getCategoryId());
		productDetailVo.setDetail(product.getDetail());
		productDetailVo.setName(product.getName());
		productDetailVo.setStatus(product.getStatus());
		productDetailVo.setStock(product.getStock());

		productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

		Category category = categoryDao.selectByPrimaryKey(product.getCategoryId());
		if (category == null) {
			//默认根节点
			productDetailVo.setParentCategoryId(0);
		} else {
			productDetailVo.setParentCategoryId(category.getParentId());
		}

		productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
		productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
		return productDetailVo;
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
