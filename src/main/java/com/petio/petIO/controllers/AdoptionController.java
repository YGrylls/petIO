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

import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.SearchInfo;
import com.petio.petIO.services.AdoptionListService;

@Controller
public class AdoptionController {
	
	@Autowired
	private AdoptionListService adoptionListService;
	
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
}
