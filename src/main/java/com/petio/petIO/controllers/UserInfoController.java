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
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.ConnectInfo;
import com.petio.petIO.beans.PasswordInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.User;
import com.petio.petIO.services.AdoptionService;
import com.petio.petIO.services.UserService;

@Controller
public class UserInfoController {
	@Autowired
	UserService userService;
	
	@Autowired
	AdoptionService adoptionService;
	
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
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/info", method = RequestMethod.GET)
	@ResponseBody
	public Result userInfo(HttpServletRequest request, HttpServletResponse response) {
		
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
		
		ConnectInfo connectInfo = userService.getConnectionByID(uid);
		
		return ResultFactory.buildSuccessResult(connectInfo);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/adoptions", method = RequestMethod.GET)
	@ResponseBody
	public Result userAdoptions(HttpServletRequest request, HttpServletResponse response) {
		
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
		
		List<Adoption> adoptions = adoptionService.getAdoptionsByUid(uid);
		
		for(Adoption adoption:adoptions) {
			if (adoption.getaMoney() == 0)
				adoption.setFree(true);
			else
				adoption.setFree(false);
			// adoption.setImgPaths(utils.getImgPaths(id)); // 获取图片路径，暂时没实现
		}
		
		return ResultFactory.buildSuccessResult(adoptions);
	}
}
