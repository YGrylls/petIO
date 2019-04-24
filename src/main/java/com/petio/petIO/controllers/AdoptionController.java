package com.petio.petIO.controllers;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.AdoptionInfo;
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
	

	@CrossOrigin
	@RequestMapping(value = "/api/adoption", method = RequestMethod.POST)
	@ResponseBody
	public Result getFosterage(@RequestBody SearchInfo searchInfoVo, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("searchText :" + searchInfoVo.getSearchText() + ", regionSelect : "
				+ searchInfoVo.getRegionSelect() + ", kindSelect : " + searchInfoVo.getKindSelect());
//		boolean isAuth = AuthCheck.isAuth(request, response, redisService, userService);
//		if (!isAuth) {
//			String message = String.format("未登录或登录失效");
//			return ResultFactory.buildAuthFailResult(message);
//		}
		int number = adoptionListService.getTotalNumber(searchInfoVo.getSearchText(), searchInfoVo.getRegionSelect(),
				searchInfoVo.getKindSelect());
		List<AdoptionInfo> adoptionList = adoptionListService.doFiler(searchInfoVo.getSearchText(),
				searchInfoVo.getRegionSelect(), searchInfoVo.getKindSelect(), searchInfoVo.getPage());
		System.out.println(number);
		int pages = number/20;
		if (number%20 > 0) {
			pages = pages+1;
		}
		return ResultFactory.buildSuccessResult(new ListData(pages, adoptionList));
	}
}
