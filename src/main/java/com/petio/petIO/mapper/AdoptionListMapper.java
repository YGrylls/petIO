package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.petio.petIO.Utils.SqlProvider;
import com.petio.petIO.beans.Adoption;


@Mapper
public interface AdoptionListMapper {
	@SelectProvider(type=SqlProvider.class,method="getAdoptionTotalNumber")
	public Integer getTotalNumber(String title,String location,String type);
	
	@SelectProvider(type=SqlProvider.class,method="getAdoption")
<<<<<<< HEAD
	public List<Adoption> getAdoption(String title,String location,String type,Integer page);
=======
	public List<Adoption> getFosterage(String title,String location,String type,Integer page);
	
	@Select("select * from Adoption where aID = #{id}")
	public Adoption getAdoptionByID(Integer id);
	
	@Select("select times from ApplyTimes where id = #{id}")
	public Integer getApplyTimes(Integer id);
	
	@Select("select count(*) from Apply where aID = #{aID} and applier = #{uID}")
	public Integer getApply(Integer aID, Integer uID);
	
	@Insert("insert into Apply (aID,applier)values(#{aID},#{uID})")
	public Integer addOffer(Integer aID,Integer uID);

	
>>>>>>> 50e572041566499252e052505228275ff5166cc4
}
