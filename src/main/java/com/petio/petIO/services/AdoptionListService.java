package com.petio.petIO.services;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.Adoption;
import com.petio.petIO.mapper.AdoptionListMapper;
@Service
public class AdoptionListService {
	@Autowired
	private AdoptionListMapper mapper;
	public List<Adoption> doFiler(String searchText,String regionSelect,String kindSelect,Integer page) {
		List<Adoption> rawList = mapper.getAdoption(searchText,regionSelect, kindSelect,page);
		System.out.println("size"+rawList.size());
		return rawList;
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
	
	public Integer addOffer(Integer aID,Integer uID){
		return mapper.addOffer(aID, uID);
	}
	
	public Integer addApplyTimes(Integer aID,Integer uID) {
		return 1;
	}
}
