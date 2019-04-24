package com.petio.petIO.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.Adoption;
import com.petio.petIO.mapper.AdoptionMapper;

@Service
public class AdoptionService {
	@Autowired
	AdoptionMapper mapper;
	
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
