package com.petio.petIO.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.UserBoxInfo;
import com.petio.petIO.services.UserService;

@RestController
public class UserBoxController {

	@Autowired
	private UserService userService;

	@PostMapping("/api/userbox/{id}")
	public Result userBox(@PathVariable("id") Integer id, HttpServletRequest request) {
		String targetUser = userService.getUsernameByID(id);
		if (targetUser == null || "".equals(targetUser)) {
			return ResultFactory.buildFailResult("UID 不存在");
		} else {
			Integer adoptionNum = userService.getAdoptionCount(id);
			Integer completeAdoptionNum = userService.getCompleteAdoptionCount(id);
			Integer completeApplyNum = userService.getCompleteApplyCount(id);
			return ResultFactory.buildSuccessResult(
					new UserBoxInfo(id, targetUser, adoptionNum, completeAdoptionNum, completeApplyNum));
		}

	}
}
