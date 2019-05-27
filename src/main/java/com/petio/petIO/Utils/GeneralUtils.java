package com.petio.petIO.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.petio.petIO.services.UserRedisService;
import com.petio.petIO.services.UserService;

public class GeneralUtils {

	// 获取图片路径
	public static List<String> getImgPaths(int id) {
		List<String> imgPaths = new ArrayList<>();
		return imgPaths;
	}

	// 通过cookie获得用户id

	public static Integer getUidByCookie(HttpServletRequest request, UserService userService,UserRedisService userRedisService) {

		if(request == null)return -1;

		String username = "";
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginStatus")) {
					if (null != cookie.getValue() && !"".equals(cookie.getValue())) {
						/**
						 * check user
						 */
						String[] token = cookie.getValue().split("_");
						username = token[0];
						System.out.println("----" + username + "-----");
						
						String sessionId = request.getSession().getId();
						String currentSessionID = userRedisService.getUserSession(username);
						if (!username.equals("") && sessionId.equals(currentSessionID)) {
							System.out.println("aaa");
							return userService.getUidByName(username);
						}
					}
				}
			}
		}
		System.out.println("bbb");
		return -1;
	}

}
