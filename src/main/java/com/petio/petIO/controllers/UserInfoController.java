package com.petio.petIO.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.PasswordInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.services.UserService;

@Controller
public class UserInfoController {
	@Autowired
	UserService userService;
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public Result changePassword(@RequestBody PasswordInfo passwordInfo,
			HttpServletRequest request, HttpServletResponse response) {
		
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
		
		if(userService.CheckPassword(uid, passwordInfo.getOldpass())) {
			userService.updatePassword(uid, passwordInfo.getNewpass());
		}
		else {
			return ResultFactory.buildFailResult("修改失败，旧密码不正确");
		}
		
		return ResultFactory.buildSuccessResult("修改成功");
	}
}
