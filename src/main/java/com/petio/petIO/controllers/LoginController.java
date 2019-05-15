package com.petio.petIO.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.petio.petIO.services.UserService;
import com.petio.petIO.services.VerifyService;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	VerifyService verifyService;

	@CrossOrigin
	@RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result login(@Valid @RequestBody LoginInfo loginInfoVo, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
			return ResultFactory.buildFailResult(message);
		}
		User user = userService.getUserByName(loginInfoVo.getUsername());
//		String password = Encoder.encryptBasedDes(loginInfoVo.getPassword());
		String password = loginInfoVo.getPassword();
		System.out.println("---" + password + "-----");
		if (user != null && user.getPassword().equals(password)) {

			HttpSession session = request.getSession();
			String sessionId = session.getId();
			userService.addUserSession(user.getUsername() + "_" + password, sessionId);

			Cookie cookie = new Cookie("loginStatus", user.getUsername() + "_" + password);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);

			return ResultFactory.buildSuccessResult("登陆成功。");
		}
		String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
		return ResultFactory.buildFailResult(message);

	}

	@CrossOrigin
	@RequestMapping(value = "/api/signup", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result signup(@Valid @RequestBody SignupInfo signupInfoVo, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		User user = userService.getUserByName(signupInfoVo.getUsername());
		if (user != null) {
			return ResultFactory.buildFailResult("用户已存在");
		}
		if (signupInfoVo.getUsername().length() > 16 || signupInfoVo.getUsername().isEmpty()
				|| signupInfoVo.getPassword().length() > 16 || signupInfoVo.getPassword().isEmpty()) {
			return ResultFactory.buildFailResult("长度需要在1-16以内");
		}
//		if (!Pattern.matches(
//				"^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$",
//				signupInfoVo.getUserTel())) {
//			return ResultFactory.buildFailResult("电话号码不符合规范");
//		}

		HttpSession session = request.getSession();
		Object verCode = session.getAttribute("verCode");
		if (null == verCode) {
			return ResultFactory.buildFailResult("验证码已失效，请重新输入");
		}
		String verCodeStr = verCode.toString();
		String code = signupInfoVo.getVerifyCode();
		System.out.println("-----" + verCodeStr + "-------" + code + "-------");
		if (verCodeStr == null || code == null || code.isEmpty() || !verCodeStr.equalsIgnoreCase(code)) {
			return ResultFactory.buildFailResult("验证码错误");
		}
		session.removeAttribute("verCode");
		User user2 = new User(signupInfoVo.getUsername(), signupInfoVo.getPassword(), signupInfoVo.getUserMail(), 0);
		userService.add(user2);
		return ResultFactory.buildSuccessResult("注册成功");
	}

	@CrossOrigin
	@RequestMapping(value = "/api/mailcodeonsignup", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result sendMailCodeOnSignup(@RequestBody String mailAddress, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(mailAddress);
		if (userService.checkMailAddress(mailAddress) > 0) {
			ResultFactory.buildFailResult("邮箱已被注册");
		}
//		if (verifyService.valid(mailAddress)) {
		String verifyCode = verifyService.generateVerifyCode(6);
		String content = "本次操作验证码为： " + "<b>" + verifyCode + "</b>";
		System.out.println(verifyService.sendMail(mailAddress, "账号注册", content));

//		}else {
//			ResultFactory.buildFailResult("邮箱无效");
//		}
		HttpSession session = request.getSession(true);
		session.removeAttribute("verCode");
		session.setAttribute("verCode", verifyCode.toLowerCase());
		return ResultFactory.buildSuccessResult("验证码发送成功");
	}

}
