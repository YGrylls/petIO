package com.petio.petIO.services;

import java.util.List;

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
}
