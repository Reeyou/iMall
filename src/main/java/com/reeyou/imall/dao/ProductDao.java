package com.reeyou.imall.dao;

import com.reeyou.imall.pojo.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}