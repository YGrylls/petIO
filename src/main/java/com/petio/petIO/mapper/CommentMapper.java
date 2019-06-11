package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petio.petIO.beans.CommentInfo;
import com.petio.petIO.beans.NewInfo;

@Mapper
public interface CommentMapper {

	@Select("select Comment.cID, Comment.commentText, u1.username as fromUser, u2.userName as toUser, Comment.pubtime from Comment inner join User u1 on Comment.from = u1.userID and Comment.aID = #{aID} left join User u2 on Comment.to = u2.userID order by Comment.pubtime")
	public List<CommentInfo> getCommentsByAdoptionID(Integer aID);
	

	@Select("select * from "
			+ " (select Comment.aID, aTitle, Comment.cID as uID,username , commentText as content , pubtime as time "
			+ " from User , Comment , Adoption "
			+ " where Comment.to = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID  "
			+ " union "
			+ " select Comment.aID, aTitle, Comment.cID as uID, username , commentText as content , pubtime as time "
			+ " from User , Comment , Adoption "
			+ " where Adoption.editor = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID ) as a "
			+ " order by time ")
	public List<NewInfo> getUnreadComments(Integer userID);
	
	@Select("select Comment.aID, aTitle, username , commentText as content , pubtime as time from User , Comment , Adoption where Comment.to = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID order by Comment.pubtime")
	public List<NewInfo> getUnreadCommentsByuserID(Integer userID);
	
//	@Select("select Comment.aID, aTitle, username , commentText as content , pubtime as time from User , Comment , Adoption where Adoption.editor = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID order by Comment.pubtime")
//	public List<NewInfo> getUnreadComments(Integer userID);
	
//	@Select("select count(*) from User , Comment , Adoption where Comment.to = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID")
//	public Integer getUnreadCommentsNumberByuserID(Integer userID);
	
	@Select("select count(*) from "
			+ " (select Comment.aID, aTitle, Comment.cID as uID, username , commentText as content , pubtime as time "
			+ " from User , Comment , Adoption "
			+ " where Comment.to = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID  "
			+ " union "
			+ " select Comment.aID, aTitle, Comment.cID as uID, username , commentText as content , pubtime as time "
			+ " from User , Comment , Adoption "
			+ " where Adoption.editor = #{userID} and User.userID = Comment.from and Adoption.aID = Comment.aID ) as a "
			+ "order by time ")
	public Integer getUnreadCommentsNumber(Integer userID);
	
	@Update("UPDATE Comment set Comment.read = 1 where aID = #{aID} and Comment.to = #{uID}")
	public Integer setAllCommentsRead(Integer aID,Integer uID);
	
	@Update("UPDATE Comment set Comment.read = 1 where Comment.to = #{userID}")
	public Integer resetCommentsRead(Integer uID);
	 
	@Update("UPDATE Comment set Comment.aRead = 1 where Comment.aID = #{aID} and Comment.aID in (select Adoption.aID from Adoption where editor = #{uID})")
	public Integer setAllCommentsOwnerRead(Integer aID,Integer uID);
	
	@Update("UPDATE Comment set Comment.aRead = 1 where Comment.aID in (select Adoption.aID from Adoption where editor = #{uID})")
	public Integer resetAllCommentsOwnerRead(Integer uID);
	
	@Update("UPDATE Comment set Comment.read = 1 where Comment.cID = #{cID} and Comment.to = #{uID}")
	public Integer readSingleComment(Integer cID,Integer uID);
	
	@Update("UPDATE Comment set Comment.aRead = 1 where  Comment.cID = #{cID} and Comment.aID in (select Adoption.aID from Adoption where editor = #{uID})")
	public Integer readOwnerSingleComment(Integer cID,Integer uID);
	
	@Update("UPDATE Comment set Comment.read = 1 where aID = #{aID} and cID = #{cID}")
	public Integer setSingleCommentRead(Integer aID,Integer cID);
	
	@Update("UPDATE Comment set Comment.aRead = 1 where aID = #{aID} and cID = #{cID}")
	public Integer setSingleCommentOwnerRead(Integer aID,Integer cID);
	
	@Insert("insert into Comment (commentText,`from`,`to`,aID)values(#{commentText},#{from},#{to},#{aID})")
	public Integer addCommentWithTo(String commentText, Integer from, Integer to, Integer aID);

	@Insert("insert into Comment (commentText,`from`,aID)values(#{commentText},#{from},#{aID})")
	public Integer addCommentNoTo(String commentText, Integer from, Integer aID);
}
