package com.petio.petIO.services;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.Candidate;
import com.petio.petIO.beans.ConnectInfo;
import com.petio.petIO.beans.NewInfo;
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
	
	
	public Integer readApply(Integer aID, Integer uID) {
		return mapper.readApply( aID,  uID);
	}
	
	public List<NewInfo> getUnreadApply(Integer uID) {
		return mapper.getUnreadApply(uID);
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
		mapper.deletePictureByid(aID);
		mapper.deleteAdoptionByid(aID);
		return 1;
	}
	
	public List<String> getImgPaths(Integer aID) {
		return mapper.getImgPaths(aID);
	}
	
	public ConnectInfo getCommunicationByID(Integer aID) {
		return mapper.getCommunicationByID(aID);
	}
	
	public Integer changeState(Integer aid, Integer state) {
		return mapper.changeState(aid, state);
	}
	
	public List<Adoption> getAdoptionsByApply(Integer uid){
		return mapper.getAdoptionsByApply(uid);
	}
	public Integer updateView(Integer aID) {
		return mapper.updateView(aID);
	}
	public Integer updateRead(Integer aID) {
		return mapper.updateRead(aID);
	}
	public Integer resetRead(Integer aID) {
		return mapper.resetRead(aID);
	}
	
	public List<Candidate> getCandidatesByAID(Integer aID){
		return mapper.getCandidatesByAID(aID);
	}
	
	public Integer addFirstHandShake(Integer aID, Integer acceptor) {
		return mapper.addFirstHandShake(aID, acceptor);
	}
	
	public boolean checkRecord(Integer aID) {
		if(mapper.checkRecord(aID) > 0)return true;
		return false;
	}
	
	public Integer addSecondHandShake(Integer aID) {
		return mapper.addSecondHandShake(aID);
	}
	
	public Integer deleteRecord(Integer aID) {
		return mapper.deleteRecord(aID);
	}
	
	public List<Adoption> getFirstAdoptions(Integer uid){
		return mapper.getFirstAdoptions(uid);
	}
	
	public Candidate getCandidateByRecord(Integer aID) {
		return mapper.getCandidateByRecord(aID);
	}
	
	
}
