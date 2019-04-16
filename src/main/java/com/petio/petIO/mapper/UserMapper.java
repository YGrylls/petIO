package com.petio.petIO.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petio.petIO.beans.User;

@Mapper
public interface UserMapper {

	@Select("select username,password,userTel,userScore from User where username = #{username}")
	public User getUserByName(String username);

	@Insert("insert into User (username,password,userTel,userScore)values(#{username},#{password},#{userTel},#{userScore})")
	public Integer add(User user);
}
