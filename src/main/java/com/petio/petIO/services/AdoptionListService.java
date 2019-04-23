package com.petio.petIO.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.AdoptionInfo;
import com.petio.petIO.mapper.AdoptionListMapper;
import com.petio.petIO.mapper.UserMapper;
@Service
public class AdoptionListService {
	@Autowired
	private AdoptionListMapper mapper;
	@Autowired
	private UserMapper userMapper;
	
	public List<AdoptionInfo> doFiler(String searchText,String regionSelect,String kindSelect,Integer page) {
		List<Adoption> rawList = mapper.getAdoption(searchText,regionSelect, kindSelect,page);
		System.out.println("size"+rawList.size());
		List<AdoptionInfo> results = new ArrayList<>();
		
		for (Adoption adoption : rawList) {
			String username = userMapper.getUsernameByID(adoption.getEditor());
			results.add(new AdoptionInfo(adoption.getaID(),
										username	, 
										adoption.getaTitle(),
										adoption.getaType(), 
										adoption.getLocation(), 
										adoption.getaText(), 
										adoption.getPublishDate(), 
										adoption.getaRead(), 
										adoption.getStartDate(), 
										adoption.getaState(), 
										adoption.getImgPaths()));
		}
		return results;
	}
	public Integer getTotalNumber(String searchText, String regionSelect, String kindSelect) {
		return mapper.getTotalNumber(searchText, regionSelect, kindSelect);
	}
	
	public Adoption getAdoptionByID(Integer id) {
		return mapper.getAdoptionByID(id);
	}
	
	public Integer getApplyTimes(Integer id) {
		return mapper.getApplyTimes(id);
	}
	
	public boolean checkApply(Integer aID, Integer uID) {
		if(mapper.getApply(aID, uID)>0)return true;
		return false;
	}
	
	public Integer addApply(Integer aID,Integer uID){
		return mapper.addApply(aID, uID);
	}
	
	public Integer addApplyTimes(Integer uID) {
		if(mapper.getApplyTimes(uID) > 0) {
			mapper.updateApplyTimes(uID);
		}
		else {
			mapper.addApplyTimes(uID);
		}
		return 1;
	}
	
	public void initApplyTimes() {
		mapper.initApplyTimes();
	}
	
}
