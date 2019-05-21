package com.petio.petIO.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.PersonInfo;
import com.petio.petIO.beans.User;
import com.petio.petIO.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRedisService userRedisService;

	public void addUserSession(String username, String sessionId) {
		userRedisService.addUserSession(username, sessionId);
	}

	public User getUserByName(String username) {
		return userMapper.getUserByName(username);
	}

	public Integer add(User user) {
		System.out.println(user);
		return userMapper.add(user);
	}

	public Integer getUidByName(String username) {
		return userMapper.getUidByName(username);
	}

	public PersonInfo getConnectionByID(int userID) {
		return userMapper.getConnectionByID(userID);
	}

	public User getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginStatus")) {
					if (null != cookie.getValue() && !"".equals(cookie.getValue())) {
						String[] token = cookie.getValue().split("_");
						String username = token[0];
						User user = this.getUserByName(username);
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
						String currentSessionID = userRedisService.getUserSession(cookie.getValue());
						System.out.println("[currentSessionID:" + currentSessionID + "]");
						System.out.println("[sessionId:" + sessionId + "]");
						if (sessionId.equals(currentSessionID)) {
							return user;
						} else {
							return null;
						}
					}
				}
			}
		}
		return null;
	}

	public boolean isAuth(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginStatus")) {
					if (null != cookie.getValue() && !"".equals(cookie.getValue())) {
						String[] token = cookie.getValue().split("_");
						String username = token[0];
						User user = this.getUserByName(username);
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
						String currentSessionID = userRedisService.getUserSession(cookie.getValue());
						System.out.println("[currentSessionID:" + currentSessionID + "]");
						System.out.println("[sessionId:" + sessionId + "]");
						if (sessionId.equals(currentSessionID)) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean CheckPassword(int uid, String password) {
		if (userMapper.CheckPassword(uid, password) > 0)
			return true;
		return false;
	}

	public Integer updatePassword(Integer uid, String newpass) {
		return userMapper.updatePassword(uid, newpass);
	}

	public Integer updatePhone(Integer uid, String newphone) {
		return userMapper.updatePhone(uid, newphone);
	}

	public int checkMailAddress(String mail) {
		return userMapper.checkMailAddress(mail);
	}

	public boolean changePassword(String mail, String password) {
		if (userMapper.changePasswordByMail(mail, password) == 1) {
			return true;
		}
		return false;
	}

	public String getUsernameByID(int userID) {
		return userMapper.getUsernameByID(userID);
	}
	public Integer getNewByName(String username) {
		int userID = userMapper.getUidByName(username);
		return userMapper.getNewByID(userID);
	}
	public Integer getNewByID(int userID) {
		return userMapper.getNewByID(userID);
	}
}
