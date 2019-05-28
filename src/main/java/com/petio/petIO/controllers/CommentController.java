package com.petio.petIO.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.petio.petIO.Utils.GeneralUtils;
import com.petio.petIO.Utils.ResultFactory;
import com.petio.petIO.beans.CommentInfo;
import com.petio.petIO.beans.NewCommentInfo;
import com.petio.petIO.beans.Result;
import com.petio.petIO.beans.User;
import com.petio.petIO.services.CommentService;
import com.petio.petIO.services.UserService;

@RestController
public class CommentController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/api/comment/get/{id}")
	public Result getComment(@PathVariable("id") Integer id, HttpServletRequest request) {
		List<CommentInfo> res = commentService.getComment(id);
		return ResultFactory.buildSuccessResult(res);
	}
	


	@PostMapping("/api/comment/publish")
	public Result publishComment(@RequestBody NewCommentInfo newComment, HttpServletRequest request,
			HttpServletResponse response) {
		if (!newComment.validate())
			return ResultFactory.buildFailResult("Comment invalid");
		User user = userService.getCurrentUser(request, response);
		System.out.println("-----------comment----" + user);
		if (null != user) {
			if (commentService.addNewComment(user, newComment))
				return ResultFactory.buildSuccessResult("CommentSuccess");
			else
				return ResultFactory.buildFailResult("Comment Failed");
		}
		return ResultFactory.buildAuthFailResult("Auth expire");
	}
}
