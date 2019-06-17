package com.reeyou.imall.service;

import com.github.pagehelper.PageInfo;
import com.reeyou.imall.common.ServerResponse;
import com.reeyou.imall.pojo.Category;

import java.util.List;

/**
 * @author Reeyou
 * @data 2019/5/18 20:48
 */
public interface CategoryService {

	ServerResponse addCategory(String categoryName, Integer parentId);

	ServerResponse updateCategory(String categoryName, Integer categoryId);

	ServerResponse<PageInfo> findCategoryList(int pageNum, int pageSize, Integer categoryId);

	ServerResponse<PageInfo> findCategoryChildrenList(int pageNum, int pageSize, Integer categoryId);

	ServerResponse findCategoryDetail(Integer categoryId);

	ServerResponse deleteCategory(Integer categoryId);

	ServerResponse updateCategoryStatus(Integer categoryId, Boolean status);

}
