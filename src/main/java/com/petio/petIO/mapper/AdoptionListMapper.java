package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.petio.petIO.Utils.SqlProvider;
import com.petio.petIO.beans.Adoption;

@Mapper
public interface AdoptionListMapper {
	@SelectProvider(type = SqlProvider.class, method = "getAdoptionTotalNumber")
	public Integer getTotalNumber(String title, String location, String type);

	@SelectProvider(type = SqlProvider.class, method = "getAdoption")
	public List<Adoption> getAdoption(String title, String location, String type, Integer page);

	@Insert("insert into Adoption(editor,aTitle,aType,location,aText,aSex,aMoney,aDetailInfo)values(#{editor},#{aTitle},#{aType},#{location},#{aText},#{aSex},#{aMoney},#{aDetailInfo})")
	public Integer addNewAdoption(Adoption adoption);
	
	@Insert("insert into Adoption(aID,editor,aTitle,aType,location,aText,aSex,aMoney,aDetailInfo)values( #{1} ,#{editor},#{aTitle},#{aType},#{location},#{aText},#{aSex},#{aMoney},#{aDetailInfo})")
	public Integer addFirstAdoption(Adoption adoption);
	
	@Select("SELECT max(aID) FROM Adoption")
	public Integer getMaxID();
	
	@Insert("insert into Picture(aID,path)values(#{aID},#{path})")
	public Integer addPic(Integer aID,String path);
	
	@Delete("DELETE FROM Picture WHERE aID = #{aID}")
	public Integer deletePictures(Integer aID);
}
