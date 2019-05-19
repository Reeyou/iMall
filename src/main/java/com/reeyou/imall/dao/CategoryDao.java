package com.reeyou.imall.dao;

import com.reeyou.imall.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryDao {
	@Delete("delete from mmall_category where id = #{id}")
    int deleteByPrimaryKey(Integer id);

	@Insert("insert into mmall_category (id, parent_id, name, status, sort_order, create_time, update_time)"
			+ "values (#{id}, #{parentId}, #{name}, #{status,jdbcType=BIT}, #{sortOrder}, now(), now())")
    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);
	@Update("update mmall_category set parent_id = #{parentId}, name = #{name}, status = #{status,jdbcType=BIT},sort_order = #{sortOrder},create_time = #{createTime}, update_time = now() where id = #{id}")
    int updateByPrimaryKey(Category record);

    @Select("select * from mmall_category where parent_id = #{parentId}")
    List<Category> selectCategoryChildrenByParentId(Integer parentId);
}