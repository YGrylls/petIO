package com.petio.petIO.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.petio.petIO.beans.User;
import com.petio.petIO.services.UserRedisService;
import com.petio.petIO.services.UserService;

public class GeneralUtils {

	// 获取图片路径
	public static List<String> getImgPaths(int id) {
		List<String> imgPaths = new ArrayList<>();
		return imgPaths;
	}

	// 通过cookie获得用户id

	public static Integer getUidByCookie(HttpServletRequest request, HttpServletResponse response, UserService userService) {

		User user = userService.getCurrentUser(request, response);
		if(user == null)return -1;
		else return userService.getUidByName(user.getUsername());
	}

}
