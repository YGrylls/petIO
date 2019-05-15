package com.petio.petIO.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petio.petIO.beans.PersonInfo;
import com.petio.petIO.beans.User;

@Mapper
public interface UserMapper {

	@Select("select username,password,userMail,userScore from User where username = #{username}")
	public User getUserByName(String username);

	@Select("select userID from User where username = #{username}")
	public Integer getUidByName(String username);

	@Select("select username from User where userID = #{userID}")
	public String getUsernameByID(int userID);

	@Insert("insert into User (username,password,userScore,userMail)values(#{username},#{password},#{userScore},#{userMail})")
	public Integer add(User user);

	@Select("select username,userMail from User where userID = #{userID}")
	public PersonInfo getConnectionByID(int userID);

	@Select("select count(*) from User where userID = #{uid} and password = #{password}")
	public Integer CheckPassword(int uid, String password);

	@Update("update User set password = #{newpass} where userID = #{uid}")
	public Integer updatePassword(Integer uid, String newpass);

	@Update("update User set userTel = #{newphone} where userID = #{uid}")
	public Integer updatePhone(Integer uid, String newphone);

	@Select("select count(*) from User where userMail = #{mail}")
	public Integer checkMailAddress(String mail);

	@Update("update User set password = #{newpass} where userMail = #{mail}")
	public Integer changePasswordByMail(String mail, String newpass);
}
