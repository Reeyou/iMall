package com.reeyou.imall.dao;

import com.reeyou.imall.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
	@Delete("delete from mmall_user where id = #{id}")
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into mmall_user (id, username, password, email, phone, question, answer, role, create_time, update_time)"
			+ "values(#{id}, #{username}, #{password},#{email}, #{phone}, #{question},#{answer}, #{role}, now(), now())")
    int insert(User record);

    int insertSelective(User record);

    @Select("select * from mmall_user where id = #{id}")
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    @Update("update mmall_user set username = #{username}, password = #{password}, email = #{email}, phone = #{phone}, role = #{role}, create_time = #{createTime}, update_time = now() where id = #{id}")
    int updateByPrimaryKey(User record);

    @Select("select count(1) from mmall_user where username = #{username}")
    int checkUsername(String username);

    @Select("select count(1) from mmall_user where email = #{email}")
    int checkEmail(String email);

    @Select("select count(1) from mmall_user where email = #{email} and username = #{username}")
	int checkEmailByUsername(@Param("email") String email, @Param("username") String username);

    @Select("select count(1) from mmall_user where password = #{password} and username = #{username}")
    int checkPasswordByUsername(@Param("password") String password, @Param("username")String username);

    @Update("update mmall_user SET password = #{newPassword},update_time = now() WHERE username = #{username}")
    int updatePwdByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

    @Select("select * from mmall_user where username = #{username} and password = #{password}")
    User matchUser(@Param("username") String username, @Param("password") String password);


}
