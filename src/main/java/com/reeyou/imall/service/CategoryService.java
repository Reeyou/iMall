package com.reeyou.imall.service;

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

	ServerResponse<List<Category>> findCategoryList(Integer categoryId);

	ServerResponse<List<String>> findCategoryChildrenList(Integer categoryId);

}
