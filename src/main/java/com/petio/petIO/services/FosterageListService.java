package com.petio.petIO.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petio.petIO.beans.Fosterage;
import com.petio.petIO.mapper.FosterageListMapper;
@Service
public class FosterageListService {
	@Autowired
	private FosterageListMapper mapper;
	public List<Fosterage> doFiler(String searchText,String regionSelect,String kindSelect,Integer page) {
		List<Fosterage> rawList = mapper.getFosterage(searchText,regionSelect, kindSelect,page);
		System.out.println("size"+rawList.size());
		return rawList;
	}
	public Integer getTotalNumber(String searchText, String regionSelect, String kindSelect) {
		return mapper.getTotalNumber(searchText, regionSelect, kindSelect);
	}
}
