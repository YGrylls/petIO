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

import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.AdoptionInfo;
import com.petio.petIO.beans.ListData;
import com.petio.petIO.beans.NewAdoptionInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.SearchInfo;
import com.petio.petIO.beans.User;
import com.petio.petIO.services.AdoptionListService;
import com.petio.petIO.services.UploadFileService;
import com.petio.petIO.services.UserService;

@Controller
public class AdoptionController {

	@Autowired
	private AdoptionListService adoptionListService;

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private UserService userService;

//	@CrossOrigin
//	@RequestMapping(value = "/api/upload", method = RequestMethod.POST)
//	@ResponseBody
//	public Result uploadFiles(@RequestParam("imgInput") MultipartFile[] files) {
//		boolean flag = true;
//		for (int i = 0; i < files.length; i++) {
//			flag&=uploadFileService.getUploadFilePath(files[0], String.valueOf(i));
//		}
//		if (flag) {
//			return ResultFactory.buildSuccessResult("Upload Successfully");
//		} 
//		return ResultFactory.buildFailResult("Upload Fail");
//	}
	@CrossOrigin
	@RequestMapping(value = "/api/new", method = RequestMethod.POST)
	@ResponseBody
	public Result addNewAdoption(@RequestBody NewAdoptionInfo newAdoptionInfo, HttpServletRequest request,
			HttpServletResponse response) {
//		userService.isAuth(request, response);
		User user = userService.getCurrentUser(request, response);
		if (null != user) {
			Adoption newAdoption = new Adoption(userService.getUidByName(user.getUsername()),
					newAdoptionInfo.getTitle(), newAdoptionInfo.getType(), newAdoptionInfo.getLocation(),
					newAdoptionInfo.getDetail(), newAdoptionInfo.getSex(), newAdoptionInfo.getCost(),
					newAdoptionInfo.getRequirements());
			if (adoptionListService.addNewAdoption(newAdoption)) {
				int newID = adoptionListService.getMaxID();
				adoptionListService.addImages(newID, newAdoptionInfo.getImgUrl());
				return ResultFactory.buildSuccessResult(newID);
			}
			return ResultFactory.buildFailResult("error");
		}
		return ResultFactory.buildAuthFailResult("Auth expire");
	}

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
		int pages = number / 20;
		if (number % 20 > 0) {
			pages = pages + 1;
		}
		return ResultFactory.buildSuccessResult(new ListData(pages, adoptionList));
	}
}
