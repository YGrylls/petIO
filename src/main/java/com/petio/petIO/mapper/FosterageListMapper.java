package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.petio.petIO.Utils.SqlProvider;
import com.petio.petIO.beans.Fosterage;


@Mapper
public interface FosterageListMapper {
	@SelectProvider(type=SqlProvider.class,method="getFosterTotalNumber")
	public Integer getTotalNumber(String title,String location,String type);
	
	@SelectProvider(type=SqlProvider.class,method="getFosterage")
	public List<Fosterage> getFosterage(String title,String location,String type,Integer page);
}
