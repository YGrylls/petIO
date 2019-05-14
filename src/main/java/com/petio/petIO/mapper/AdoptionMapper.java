package com.petio.petIO.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petio.petIO.beans.Adoption;
import com.petio.petIO.beans.ConnectInfo;

@Mapper
public interface AdoptionMapper {
	
	@Select("select * from Adoption where aID = #{id}")
	public Adoption getAdoptionByID(Integer id);
	
	@Select("select count(*) from Apply where aID = #{aID} and applier = #{uID}")
	public Integer checkApply(Integer aID, Integer uID);
	
	@Insert("insert into Apply (aID,applier)values(#{aID},#{uID})")
	public Integer addApply(Integer aID,Integer uID);
	
	@Select("select count(*) from ApplyTimes where uID = #{uID}")
	public Integer checkApplyTimes(Integer uID);
	
	@Select("select times from ApplyTimes where uID = #{uID}")
	public Integer getApplyTimes(Integer uID);

	@Update("update ApplyTimes set times = times + 1 where uID = #{uID}")
	public Integer updateApplyTimes(Integer uID);
	
	@Insert("insert into ApplyTimes (uID,times)values(#{uID},1)")
	public Integer addApplyTimes(Integer uID);
	
	@Update("truncate table ApplyTimes")
	public Integer initApplyTimes();
	
	@Select("select * from Adoption where editor = #{uid}")
	public List<Adoption> getAdoptionsByUid(Integer uid);
	
	@Update("update Adoption set expireDate = #{date} where aID = #{aid}")
	public Integer delayDate(Integer aid, Date date);
	
	@Update("update Adoption set aState = #{state} where aID = #{aid}")
	public Integer changeState(Integer aid, Integer state);
	
	@Delete("delete from Adoption where aID = #{aID}")
	public Integer deleteAdoptionByid(Integer aID);
	
	@Delete("delete from Act where aID = #{aID}")
	public Integer deleteActByid(Integer aID);
	
	@Delete("delete from Apply where aID = #{aID}")
	public Integer deleteApplyByid(Integer aID);
	
	@Delete("delete from Picture where aID = #{aID}")
	public Integer deletePictureByid(Integer aID);
	
	@Select("select path from Picture where aID = #{aID}")
	public List<String> getImgPaths(Integer aID);
	
	@Select("select communicationType,communication from Adoption where aID = #{aID}")
	public ConnectInfo getCommunicationByID(Integer aID);
	
	@Select("select * from Adoption where aID in (select aID from Apply where applier = #{uid}) and aState = 1")
	public List<Adoption> getAdoptionsByApply(Integer uid);
}
