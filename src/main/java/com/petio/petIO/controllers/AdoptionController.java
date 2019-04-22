package com.petio.petIO.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.AuthCheck;
import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.ListData;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.SearchInfo;
import com.petio.petIO.services.AdoptionListService;
import com.petio.petIO.services.UserRedisService;
import com.petio.petIO.services.UserService;

@Controller
public class AdoptionController {
	
	@Autowired
	private AdoptionListService adoptionListService;
	
	@Autowired
	private UserRedisService redisService;
	
	@Autowired
	private UserService userService;
	private GeneralUtils utils;
	
	@CrossOrigin
	@RequestMapping(value = "/api/adoption", method = RequestMethod.POST)
	@ResponseBody
	public Result getFosterage(@RequestBody SearchInfo searchInfoVo,
			BindingResult bindingResult,HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("searchText :"+searchInfoVo.getSearchText()+", regionSelect : "+searchInfoVo.getRegionSelect()
		+", kindSelect : "+ searchInfoVo.getKindSelect());
		boolean isAuth = AuthCheck.isAuth(request, response,redisService,userService);
		if (!isAuth) {
			String message = String.format("登陆失败，详细信息[用户信息验证失败]。");
			return ResultFactory.buildFailResult(message);
		}
		int number = adoptionListService.getTotalNumber(searchInfoVo.getSearchText(),
														searchInfoVo.getRegionSelect(), 
														searchInfoVo.getKindSelect());
		List<Adoption> adoptionList = adoptionListService.doFiler(searchInfoVo.getSearchText(),
																	searchInfoVo.getRegionSelect(), 
																	searchInfoVo.getKindSelect(), 
																	searchInfoVo.getPage());
		System.out.println(number);
		return ResultFactory.buildSuccessResult(new ListData(number, adoptionList));
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/adoption/detail/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result getDetail(@RequestBody Integer id, BindingResult bindingResult) {
		Adoption adoption = adoptionListService.getAdoptionByID(id);
		if(adoption == null)return ResultFactory.buildFailResult("未找到用户");
		
		adoption.setImgPaths(utils.getImgPaths(id));   //获取图片路径，暂时没实现
		
		
		
		return ResultFactory.buildSuccessResult(adoption);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/adoption/apply", method = RequestMethod.POST)
	@ResponseBody
	public Result Apply(@RequestBody Integer id,
			BindingResult bindingResult) {
		
		int uid = 1;   //通过session获取用户id
		int times = adoptionListService.getApplyTimes(uid);  //获取用户当前申请次数 
		if(times > 3)return ResultFactory.buildFailResult("超过今日申请次数！");
		
		if(!adoptionListService.checkApply(id, uid)) {    //没申请过
			adoptionListService.addOffer(id, uid);
			adoptionListService.addApplyTimes(id, uid);     //增加今日申请次数，暂时没实现
			return ResultFactory.buildSuccessResult("申请成功！");
		}
		else {
			return ResultFactory.buildSuccessResult("您已申请过！");
		}
	}
}
