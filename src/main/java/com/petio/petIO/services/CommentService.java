package com.petio.petIO.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petio.petIO.beans.CommentInfo;
import com.petio.petIO.beans.NewCommentInfo;
import com.petio.petIO.beans.NewInfo;
import com.petio.petIO.beans.User;
import com.petio.petIO.mapper.CommentMapper;
import com.petio.petIO.mapper.UserMapper;

@Service
public class CommentService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Transactional
	public boolean addNewComment(User user, NewCommentInfo comment) {

		Integer fromUID = userMapper.getUidByName(user.getUsername());
		if (fromUID == null)
			return false;
		if ("".equals(comment.getToUsername())) {
			if (commentMapper.addCommentNoTo(comment.getCommentText(), fromUID, comment.getaID()) == 1)
				return true;
		} else {
			Integer toUID = userMapper.getUidByName(comment.getToUsername());
			if (toUID == null)
				return false;
			if (commentMapper.addCommentWithTo(comment.getCommentText(), fromUID, toUID, comment.getaID()) == 1)
				return true;
		}
		return false;
	}
	public List<NewInfo> getUnreadComment(Integer userID) {
		
		return commentMapper.getUnreadComments(userID);
	}
	public int getUnreadCommentsNumber(Integer userID) {
		
		return commentMapper.getUnreadCommentsNumber(userID);
	}
	
	public Integer setAllCommentsRead(Integer aID,Integer uID) {
		commentMapper.setAllCommentsOwnerRead(aID, uID);
		return commentMapper.setAllCommentsRead(aID, uID);
	}
	public Integer resetAllComments(Integer uID) {
		commentMapper.resetAllCommentsOwnerRead(uID);
		return commentMapper.resetCommentsRead(uID);
	}
	public void readSingleComment(Integer cID,Integer uID) {
		commentMapper.readOwnerSingleComment(cID, uID);
		commentMapper.readSingleComment(cID, uID);
	}
	
//	public Integer setSingleCommentRead(Integer aID,Integer cID) {
//		commentMapper.setSingleCommentOwnerRead(aID, cID);
//		return commentMapper.setSingleCommentRead(aID,cID);
//	}
	
	public List<CommentInfo> getComment(Integer aID) {
		List<CommentInfo> queryRes = commentMapper.getCommentsByAdoptionID(aID);
		return queryRes;
	}
}
