package com.petio.petIO.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.SearchInfo;
import com.petio.petIO.services.AdoptionListService;

@Controller
public class AdoptionController {
	
	@Autowired
	private AdoptionListService adoptionListService;
	@Autowired
	private GeneralUtils utils;
	
	@CrossOrigin
	@RequestMapping(value = "/api/adoption", method = RequestMethod.POST)
	@ResponseBody
	public Result getFosterage(@RequestBody SearchInfo searchInfoVo,
			BindingResult bindingResult) {
		System.out.println("searchText :"+searchInfoVo.getSearchText()+", regionSelect : "+searchInfoVo.getRegionSelect()
		+", kindSelect : "+ searchInfoVo.getKindSelect());
		List<Adoption> adoptionList = adoptionListService.doFiler(searchInfoVo.getSearchText(),
																	searchInfoVo.getRegionSelect(), 
																	searchInfoVo.getKindSelect(), 
																	searchInfoVo.getPage());
		System.out.println(adoptionList);
		return ResultFactory.buildSuccessResult(adoptionList);
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
			adoptionListService.addApply(id, uid);
			adoptionListService.addApplyTimes(uid);     //增加今日申请次数
			return ResultFactory.buildSuccessResult("申请成功！");
		}
		else {
			return ResultFactory.buildSuccessResult("您已申请过！");
		}
	}
}
