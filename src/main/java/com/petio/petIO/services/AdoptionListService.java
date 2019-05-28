package com.petio.petIO.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.AdoptionInfo;
import com.petio.petIO.mapper.AdoptionListMapper;
import com.petio.petIO.mapper.AdoptionMapper;
import com.petio.petIO.mapper.UserMapper;
@Service
public class AdoptionListService {
	@Autowired
	private AdoptionListMapper mapper;
	
	@Autowired
	private AdoptionMapper adoptionMapper;
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
										adoption.getExpireDate(), 
										adoption.getaState(), 
										adoptionMapper.getImgPaths(adoption.getaID())));
		}
		return results;
	}
	public Integer getTotalNumber(String searchText, String regionSelect, String kindSelect) {
		return mapper.getTotalNumber(searchText, regionSelect, kindSelect);
	}
	public boolean  addNewAdoption(Adoption adoption) {
		if (mapper.addNewAdoption(adoption)==1) {
			return true;
		}
		return false;
	}
	public boolean  addFirstAdoption(Adoption adoption) {
		if (mapper.addFirstAdoption(adoption)==1) {
			return true;
		}
		return false;
	}
	public Integer getMaxID() {
		return mapper.getMaxID();
	}
	public boolean addImages(Integer aID,String[] imgUrls) {
		int count = 0;
		for (String path : imgUrls) {
			count += mapper.addPic(aID, path);
		}
		if (count==imgUrls.length) {
			return true;
		}
		return false;
	}
	public boolean deleteImages(Integer aID) {
		if (mapper.deletePictures(aID)>0) {
			return true;
		}
		return false;
	}
}
