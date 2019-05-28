package com.petio.petIO.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petio.petIO.beans.CommentInfo;

@Mapper
public interface CommentMapper {

	@Select("select Comment.cID, Comment.commentText, u1.username as fromUser, u2.userName as toUser, Comment.pubtime from Comment inner join User u1 on Comment.from = u1.userID and Comment.aID = #{aID} left join User u2 on Comment.to = u2.userID order by Comment.pubtime")
	public List<CommentInfo> getCommentsByAdoptionID(Integer aID);
	
	@Select("select Comment.cID, Comment.commentText, u1.username as fromUser, u2.userName as toUser, Comment.pubtime from Comment inner join User u1 on Comment.from = u1.userID and u1.userID = #{userID} left join User u2 on Comment.to = u2.userID where Comment.read = 0 order by Comment.pubtime")
	public List<CommentInfo> getUnreadCommentsByAdoptionID(Integer userID);
	
	@Update("UPDATE Comment set read = 1 where aID = #{aID}")
	public Integer setAllCommentsRead(Integer aID);
	
	@Update("UPDATE Comment set read = 1 where aID = #{aID} and cID = #{cID}")
	public Integer setSingleCommentRead(Integer aID,Integer cID);
	
	@Insert("insert into Comment (commentText,`from`,`to`,aID)values(#{commentText},#{from},#{to},#{aID})")
	public Integer addCommentWithTo(String commentText, Integer from, Integer to, Integer aID);

	@Insert("insert into Comment (commentText,`from`,aID)values(#{commentText},#{from},#{aID})")
	public Integer addCommentNoTo(String commentText, Integer from, Integer aID);
}
