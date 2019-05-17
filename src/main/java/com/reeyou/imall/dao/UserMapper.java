package com.reeyou.imall.dao;

import com.reeyou.imall.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    @Select("insert into mmall_user (id, username, password, email, phone, question, answer, role, create_time, update_time)" + "values(#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, #{question,jdbcType=VARCHAR},#{answer,jdbcType=VARCHAR}, #{role,jdbcType=INTEGER}, now(), now())")
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select count(1) from mmall_user where username = #{username}")
    int checkUsername(String username);

    @Select("select count(1) from mmall_user where email = #{email}")
    int checkEmail(String email);

    @Select("select * from mmall_user where username = #{username} and password = #{password}")
    User selectLogin(@Param("username") String username, @Param("password") String password);

    String selectQuestionByUsername(String username);
}
