package com.petio.petIO.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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
	
}
