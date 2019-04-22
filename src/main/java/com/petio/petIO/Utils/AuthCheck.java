package com.petio.petIO.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.petio.petIO.beans.User;
import com.petio.petIO.services.UserRedisService;
import com.petio.petIO.services.UserService;

public class AuthCheck {
	
//	@Autowired
//	private UserRedisService redisService;
//	
//	@Autowired
//	private UserService userService;
//	
//	UserRedisService getUserRedisService() {
//		return redisService;
//	}
//	UserService getUserService() {
//		return userService;
//	}
	
	public static boolean isAuth(HttpServletRequest request,
			HttpServletResponse response,UserRedisService redisService,UserService userService) {

		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginStatus")) {
					if (null != cookie.getValue() && !"".equals(cookie.getValue())) {
						String[] token = cookie.getValue().split("_");
						String username = token[0];
						User user = userService.getUserByName(username);
//						String password = Encoder.decryptBasedDes(token[1]);
						String password = token[1];
						if (!password.equals(user.getPassword())) {
							Cookie loginStatus = new Cookie("loginStatus", null);
							loginStatus.setPath("/");
							loginStatus.setMaxAge(0);
							response.addCookie(loginStatus);
							break;
						}
						HttpSession session = request.getSession();
						String sessionId = session.getId();
						String currentSessionID = redisService.getUserSession(cookie.getValue());
						System.out.println("[currentSessionID:" + currentSessionID + "]");
						System.out.println("[sessionId:" + sessionId + "]");
						if (sessionId.equals(currentSessionID)) {
							return true;
						}else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
}
