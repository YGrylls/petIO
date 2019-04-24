package com.petio.petIO.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.ConnectInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.services.AdoptionService;
import com.petio.petIO.services.UserService;

@Controller
public class AdoptionDetailController {
	@Autowired
	AdoptionService adoptionService;

	@Autowired
	UserService userService;

	@CrossOrigin
	@RequestMapping(value = "/api/adoption/detail/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result getDetail(@PathVariable("id") Integer id) {
		System.out.println("id:" + id);
		Adoption adoption = adoptionService.getAdoptionByID(id);
		System.out.println(adoption);
		if (adoption == null)
			return ResultFactory.buildFailResult("未找到帖子");

		if (adoption.getaMoney() == 0)
			adoption.setFree(true);
		else
			adoption.setFree(false);
		// adoption.setImgPaths(utils.getImgPaths(id)); // 获取图片路径，暂时没实现

		System.out.println(adoption);

		return ResultFactory.buildSuccessResult(adoption);
	}

	@CrossOrigin
	@RequestMapping(value = "/api/adoption/apply/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result Apply(@PathVariable("id") Integer id, HttpServletRequest request) {
		System.out.println("id:" + id);
		int uid = -1; // 通过Cookie获取用户id
		try {
			uid = GeneralUtils.getUidByCookie(request);
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("申请失败，您未登录");

		int times = adoptionService.getApplyTimes(uid); // 获取用户当前申请次数
		if (times > 3)
			return ResultFactory.buildFailResult("超过今日申请次数！");

		if (!adoptionService.checkApply(id, uid)) { // 没申请过
			adoptionService.addApply(id, uid);
			adoptionService.addApplyTimes(uid); // 增加今日申请次数

		}
		Adoption adoption = adoptionService.getAdoptionByID(id);
		ConnectInfo connectInfo = userService.getConnectionByID(adoption.getEditor());

		return ResultFactory.buildSuccessResult(connectInfo);
	}
}
