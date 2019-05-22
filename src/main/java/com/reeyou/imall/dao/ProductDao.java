package com.reeyou.imall.dao;

import com.reeyou.imall.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectProductList();

	List<Product> selectByNameAndProductId(@Param("productName")String productName, @Param("productId") Integer productId);

//	List<Product> selectByNameAndCategoryIds(@Param("productName")String productName,@Param("categoryIdList")List<String> categoryIdList);
}