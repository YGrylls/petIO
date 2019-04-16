package com.petio.petIO.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.LoginInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.SignupInfo;
import com.petio.petIO.beans.User;
import com.petio.petIO.mapper.UserMapper;
import com.petio.petIO.services.UserService;

@Controller
public class LoginController {
	@Autowired
	UserService userService;

	@CrossOrigin
	@RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result login(@Valid @RequestBody LoginInfo loginInfoVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
			return ResultFactory.buildFailResult(message);
		}

		User user = userService.getUserByName(loginInfoVo.getUsername());
		if (user != null && user.getPassword().equals(loginInfoVo.getPassword())) {
			return ResultFactory.buildSuccessResult("登陆成功。");
		}
		String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
		return ResultFactory.buildFailResult(message);

	}

	@CrossOrigin
	@RequestMapping(value = "/api/signup", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result signup(@Valid @RequestBody SignupInfo signupInfoVo, BindingResult bindingResult) {
		User user = userService.getUserByName(signupInfoVo.getUsername());
		if (user != null) {
			return ResultFactory.buildFailResult("用户已存在");
		}
		
		User user2 = new User(signupInfoVo.getUsername(), signupInfoVo.getPassword(), signupInfoVo.getUserTel(), 0);
		userService.add(user2);
		return ResultFactory.buildSuccessResult("注册成功");
	}

}
