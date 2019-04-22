package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.petio.petIO.Utils.SqlProvider;
import com.petio.petIO.beans.Adoption;


@Mapper
public interface AdoptionListMapper {
	@SelectProvider(type=SqlProvider.class,method="getAdoptionTotalNumber")
	public Integer getTotalNumber(String title,String location,String type);
	
	@SelectProvider(type=SqlProvider.class,method="getAdoption")

	public List<Adoption> getAdoption(String title,String location,String type,Integer page);

	public List<Adoption> getFosterage(String title,String location,String type,Integer page);
	
	@Select("select * from Adoption where aID = #{id}")
	public Adoption getAdoptionByID(Integer id);
	
	@Select("select count(*) from Apply where aID = #{aID} and applier = #{uID}")
	public Integer getApply(Integer aID, Integer uID);
	
	@Insert("insert into Apply (aID,applier)values(#{aID},#{uID})")
	public Integer addApply(Integer aID,Integer uID);
	
	@Select("select times from ApplyTimes where uID = #{uID}")
	public Integer getApplyTimes(Integer uID);

	@Update("update ApplyTimes set times = times + 1 where uID = #{uID}")
	public Integer updateApplyTimes(Integer uID);
	
	@Insert("insert into ApplyTimes (uID,times)values(#{uID},1)")
	public Integer addApplyTimes(Integer uID);
	
	@Update("truncate table ApplyTimes")
	public Integer initApplyTimes();
}
