package com.petio.petIO.services;

import java.sql.Date;
import java.util.List;

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
		if(mapper.checkApplyTimes(id) == 0)return 0;
		return mapper.getApplyTimes(id);
	}
	
	public boolean checkApply(Integer aID, Integer uID) {
		if(mapper.checkApply(aID, uID)>0)return true;
		return false;
	}
	
	public Integer addApply(Integer aID,Integer uID){
		if(mapper.checkApply(aID, uID)>0)return -1;
		return mapper.addApply(aID, uID);
	}
	
	public Integer addApplyTimes(Integer uID) {
		if(mapper.checkApplyTimes(uID) > 0) {
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
	
	public List<Adoption> getAdoptionsByUid(Integer uid){
		return mapper.getAdoptionsByUid(uid);
	}
	
	public Integer delayDate(Integer aid, Date date) {
		return mapper.delayDate(aid, date);
	}
	
	public Integer deleteAdoption(Integer aID) {
		mapper.deleteActByid(aID);
		mapper.deleteApplyByid(aID);
		mapper.deleteAdoptionByid(aID);
		return 1;
	}
}
