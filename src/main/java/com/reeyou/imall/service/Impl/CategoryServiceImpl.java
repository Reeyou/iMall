package com.reeyou.imall.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.reeyou.imall.common.ResponseEnums;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.dao.CategoryDao;
import com.reeyou.imall.pojo.Category;
import com.reeyou.imall.service.CategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Reeyou
 * @data 2019/5/18 20:49
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	/**
	 * 添加品类信息
	 *
	 * @param categoryName
	 * @param parentId
	 * @return
	 */
	@Override
	public ServerResponse addCategory(String categoryName, Integer parentId) {
		if (StringUtils.isBlank(categoryName) && parentId == null) {
			return ServerResponse.serverErrorMsg("添加品类参数错误");
		}

		Category category = new Category();
		category.setName(categoryName);
		category.setParentId(parentId);
		category.setStatus(true);

		int resultCount = categoryDao.insert(category);
		if (resultCount == 0) {
			return ServerResponse.serverErrorMsg("添加品类失败");
		}
		return ServerResponse.serverSuccussMsg("添加品类成功");
	}

	/**
	 * 更新品类信息
	 *
	 * @param categoryName
	 * @param categoryId
	 * @return
	 */
	@Override
	public ServerResponse updateCategory(String categoryName, Integer categoryId) {
		if (StringUtils.isBlank(categoryName) && categoryId == null) {
			return ServerResponse.serverErrorMsg("更新品类名称参数错误");
		}
		Category category = new Category();
		category.setName(categoryName);
		category.setId(categoryId);

		int resultCount = categoryDao.updateByPrimaryKeySelective(category);
		if (resultCount == 0) {
			return ServerResponse.serverErrorMsg("更新品类信息失败");
		}
		return ServerResponse.serverSuccussMsg("更新品类名称成功");
	}

	/**
	 * 更改品类状态
	 * @param status
	 * @return
	 */
	public ServerResponse updateCategoryStatus(Integer categoryId, Boolean status) {
		if(status == null) {
			return ServerResponse.serverErrorMsg("更改品类状态参数错误");
		}
		Category category = new Category();
		category.setId(categoryId);
		category.setStatus(status);

		int resultCount = categoryDao.updateByPrimaryKeySelective(category);
		if(resultCount > 0) {
			return ServerResponse.serverSuccussMsg("更改品类状态成功");
		}
		return ServerResponse.serverErrorMsg("更改品类状态失败");
	}

	/**
	 * 查找总品类
	 *
	 * @param categoryId
	 * @return
	 */
	@Override
	public ServerResponse<PageInfo> findCategoryList(int pageNum, int pageSize, Integer categoryId) {
		PageHelper.startPage(pageNum, pageSize);

		List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(categoryId);
		if (CollectionUtils.isEmpty(categoryList)) {
			return ServerResponse.serverErrorMsg("未找到当前分类的子分类");
		}

		PageInfo pageResult = new PageInfo(categoryList);
		pageResult.setList(categoryList);
		return ServerResponse.serverSuccuss(pageResult);
	}

	/**
	 * 获取单个品类信息
	 *
	 * @param categoryId
	 * @return
	 */
	@Override
	public ServerResponse findCategoryDetail(Integer categoryId) {

		Category categoryDetail = categoryDao.selectByPrimaryKey(categoryId);
		if (categoryDetail == null) {
			return ServerResponse.serverErrorMsg("未找到当前分类信息");
		}

		return ServerResponse.serverSuccuss(categoryDetail);
	}

	/**
	 * 查找该品类下的子品类
	 *
	 * @param parentId
	 * @return
	 */
	@Override
	public ServerResponse<PageInfo> findCategoryChildrenList(int pageNum, int pageSize, Integer parentId) {
		PageHelper.startPage(pageNum, pageSize);

		Set<Category> categorySet = Sets.newHashSet();
		findCategoryChildren(categorySet, parentId);

		List<Category> categoryList = Lists.newArrayList();
		if (parentId != null) {
			for (Category categoryItem : categorySet) {
				categoryList.add(categoryItem);
			}
		}
		PageInfo pageResult = new PageInfo(categoryList);
		pageResult.setList(categoryList);
		return ServerResponse.serverSuccuss(pageResult);
	}

	//递归算法查找子节点方法
	public Set<Category> findCategoryChildren(Set<Category> categorySet, Integer parentId) {

		List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(parentId);
		for (Category categoryItem : categoryList) {
			//循环出来的数据放入set集合
			categorySet.add(categoryItem);
			findCategoryChildren(categorySet, categoryItem.getId());
		}
		return categorySet;
	}

	// 删除分类
	public ServerResponse deleteCategory(Integer categoryId) {
		if (categoryId == null) {
			return ServerResponse.serverErrorCodeMsg(ResponseEnums.ERROR_AUGUMENTS.getCode(), ResponseEnums.ERROR_AUGUMENTS.getDesc());
		}
		int resultCount = categoryDao.deleteByPrimaryKey(categoryId);
		if (resultCount > 0) {
			return ServerResponse.serverSuccussMsg("删除分类成功");
		}
		return ServerResponse.serverErrorMsg("删除分类失败");

	}
}