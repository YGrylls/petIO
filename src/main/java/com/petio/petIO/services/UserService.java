package com.petio.petIO.services;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.ConnectInfo;
import com.petio.petIO.beans.User;
import com.petio.petIO.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	public User getUserByName(String username) {
		return userMapper.getUserByName(username);
	}
	
	public Integer add(User user) {
		return userMapper.add(user);
	}
	
	public Integer getUidByName(String username) {
		return userMapper.getUidByName(username);
	}
	
	public ConnectInfo getConnectionByID(int userID) {
		return userMapper.getConnectionByID(userID);
	}
}
