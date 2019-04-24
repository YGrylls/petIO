package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.petio.petIO.Utils.SqlProvider;
import com.petio.petIO.beans.Adoption;

@Mapper
public interface AdoptionListMapper {
	@SelectProvider(type = SqlProvider.class, method = "getAdoptionTotalNumber")
	public Integer getTotalNumber(String title, String location, String type);

	@SelectProvider(type = SqlProvider.class, method = "getAdoption")
	public List<Adoption> getAdoption(String title, String location, String type, Integer page);

	public List<Adoption> getFosterage(String title, String location, String type, Integer page);

}
