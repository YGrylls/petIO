package com.petio.petIO.controllers;

import static org.assertj.core.api.Assertions.in;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.Candidate;
import com.petio.petIO.beans.ConnectInfo;
import com.petio.petIO.beans.FirstHandShake;
import com.petio.petIO.beans.PasswordInfo;
import com.petio.petIO.beans.PersonInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.SecondHandShake;
import com.petio.petIO.beans.User;
import com.petio.petIO.services.AdoptionService;
import com.petio.petIO.services.UserService;
import com.petio.petIO.services.VerifyService;

@Controller
public class UserInfoController {
	@Autowired
	UserService userService;
	
	@Autowired
	AdoptionService adoptionService;
	
	@Autowired
	VerifyService verifyService;
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public Result changePassword(@RequestBody PasswordInfo passwordInfo,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
    	Object verCode = session.getAttribute("verCode");
    	if (null == verCode) {
    		return ResultFactory.buildFailResult("验证码已失效，请重新输入");
    	}
    	String verCodeStr = verCode.toString();
    	String code = passwordInfo.getVerifyCode();
    	if(verCodeStr == null || code == null || code.isEmpty() || !verCodeStr.equalsIgnoreCase(code)){
    		return ResultFactory.buildFailResult("验证码错误");
    	}
		session.removeAttribute("verCode");	
		
		userService.changePassword(passwordInfo.getMailAddress(),passwordInfo.getPassword());
//		int uid = -1; 
//		try {
//			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
//		} catch (Exception e) {
//			System.out.println("fuck:" + e.getMessage());
//			e.printStackTrace();
//		}
//		if (uid == -1)
//			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
//		
//		if(userService.CheckPassword(uid, passwordInfo.getOldpass())) {
//			userService.updatePassword(uid, passwordInfo.getNewpass());
//		}
//		else {
//			return ResultFactory.buildFailResult("修改失败，旧密码不正确");
//		}
		
		return ResultFactory.buildSuccessResult("修改成功");
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/mailcodeonchangepwd", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Result sendMailCodeOnSignup(@RequestBody String mailAddress,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(mailAddress);
		if (userService.checkMailAddress(mailAddress)!=1) {
			ResultFactory.buildFailResult("账号邮箱未注册");
		}
//		if (verifyService.valid(mailAddress)) {
			String verifyCode = verifyService.generateVerifyCode(6);
			String content = "本次操作验证码为： "+"<b>"+verifyCode +"</b>";
			verifyService.sendMail(mailAddress, "账号密码修改", content);
//		}else {
//			ResultFactory.buildFailResult("邮箱无效");
//		}
		HttpSession session = request.getSession(true);
		session.removeAttribute("verCode");
		session.setAttribute("verCode", verifyCode.toLowerCase());
		return ResultFactory.buildSuccessResult("验证码发送成功");
	}
	
//	@CrossOrigin
//	@RequestMapping(value = "/api/userinfo/changePhone", method = RequestMethod.POST)
//	@ResponseBody
//	public Result changePhone(@RequestBody PersonInfo personInfo,
//			HttpServletRequest request, HttpServletResponse response) {
//		
//		int uid = -1; 
//		try {
//			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
//		} catch (Exception e) {
//			System.out.println("fuck:" + e.getMessage());
//			e.printStackTrace();
//		}
//		if (uid == -1)
//			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
//		
//		if (!Pattern.matches(
//				"^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$",
//				personInfo.getUserTel())) {
//			return ResultFactory.buildFailResult("电话号码不符合规范");
//		}
//		
//		userService.updatePhone(uid, personInfo.getUserTel());
//		
//		return ResultFactory.buildSuccessResult("修改成功");
//	}
	
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
		
		PersonInfo personInfo = userService.getConnectionByID(uid);
		
		return ResultFactory.buildSuccessResult(personInfo);
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
			adoptionService.resetRead(adoption.getaID());
			if (adoption.getaMoney() == 0)
				adoption.setFree(true);
			else
				adoption.setFree(false);
				adoption.setImgPaths(adoptionService.getImgPaths(adoption.getaID())); // 获取图片路径
		}
		return ResultFactory.buildSuccessResult(adoptions);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/delay/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result delay(@PathVariable("id") Integer id) {
		System.out.println("id:" + id);
		Adoption adoption = adoptionService.getAdoptionByID(id);
		System.out.println(adoption);
		
		Date date = new Date();
		
		long day = (adoption.getExpireDate().getTime() - date.getTime())/(24*3600*1000);
		
		System.out.println("天数差:"+ day);
		
		if(day >= 3) return ResultFactory.buildFailResult("续期失败,过期前3天内才可续期！");
		
		long delay = 15*24*3600*1000;
		
		Date newdate = new Date(adoption.getExpireDate().getTime()+delay);
		if(adoption.getExpireDate().getTime() < date.getTime()) {  //已过期
			newdate = new Date(date.getTime()+delay);
			adoptionService.changeState(id, 1);
		}
		
		adoptionService.delayDate(id, new java.sql.Date(newdate.getTime()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(newdate));
		
		return ResultFactory.buildSuccessResult(df.format(newdate));
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(@PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
		
		Adoption adoption = adoptionService.getAdoptionByID(id);
		if(adoption == null || adoption.getEditor() != uid)
			return ResultFactory.buildAuthFailResult("删除失败，您无法删除他人的帖子");
		
		List<String> imgPaths = adoptionService.getImgPaths(id);
		
		for(String s:imgPaths) {
			s = s.replace("http://localhost:8081/image/", "");
			File path = new File(ResourceUtils.getURL("classpath:").getPath()+"\\static\\images\\upload\\" + s);
			System.out.println(path.getPath());
			path.delete();
		}
		
		adoptionService.deleteAdoption(id);
		
		return ResultFactory.buildSuccessResult("删除成功");
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/apply", method = RequestMethod.GET)
	@ResponseBody
	public Result userApply(HttpServletRequest request, HttpServletResponse response) {
		
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("申请失败，您未登录");
		
		List<Adoption> adoptions = adoptionService.getAdoptionsByApply(uid);
		
		for(Adoption adoption:adoptions) {
			if (adoption.getaMoney() == 0)
				adoption.setFree(true);
			else
				adoption.setFree(false);
				adoption.setImgPaths(adoptionService.getImgPaths(adoption.getaID())); // 获取图片路径
		}
		
		return ResultFactory.buildSuccessResult(adoptions);
	}
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/getreadstate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result getReadState(@PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response){
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("用户未登录");
		int newInfo = userService.getNewByID(uid);

		
		return ResultFactory.buildSuccessResult(newInfo);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/getCandidates/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result getCandidates(@PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response){
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("用户未登录");
		
		if(adoptionService.checkRecord(id))return ResultFactory.buildFailResult("已选择候选人！");
		
		List<Candidate> candidates = adoptionService.getCandidatesByAID(id);
		
		return ResultFactory.buildSuccessResult(candidates);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/firstHandShake", method = RequestMethod.POST)
	@ResponseBody
	public Result firstHandShake(@RequestBody FirstHandShake firstHandShake,
			HttpServletRequest request, HttpServletResponse response){
		
		int uid = userService.getUidByName(firstHandShake.getUsername());
		
		adoptionService.addFirstHandShake(firstHandShake.getaID(), uid);
		
		return ResultFactory.buildSuccessResult("成功");
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/AfterFirst", method = RequestMethod.GET)
	@ResponseBody
	public Result AfterFirstHandShake(HttpServletRequest request, HttpServletResponse response){
		int uid = -1; 
		try {
			uid = GeneralUtils.getUidByCookie(request,userService); // 通过Cookie获取用户id
		} catch (Exception e) {
			System.out.println("fuck:" + e.getMessage());
			e.printStackTrace();
		}
		if (uid == -1)
			return ResultFactory.buildAuthFailResult("用户未登录");
		
		List<Adoption> adoptions = adoptionService.getFirstAdoptions(uid);
		
		return ResultFactory.buildSuccessResult(adoptions);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/userinfo/secondHandShake", method = RequestMethod.POST)
	@ResponseBody
	public Result secondHandShake(@RequestBody SecondHandShake secondHandShake,
			HttpServletRequest request, HttpServletResponse response){
		
		if(secondHandShake.isAgree()) {
			adoptionService.addSecondHandShake(secondHandShake.getaID());
			adoptionService.changeState(secondHandShake.getaID(), 5);
		}
		else {
			adoptionService.deleteRecord(secondHandShake.getaID());
		}
		
		return ResultFactory.buildSuccessResult("成功");
	}
}
