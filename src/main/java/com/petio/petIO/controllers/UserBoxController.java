package com.petio.petIO.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petio.petIO.beans.Result;
import com.petio.petIO.services.UserService;

@RestController
public class UserBoxController {

	@Autowired
	private UserService userService;

	@PostMapping("/api/userbox/{id}")
	public Result userBox(@PathVariable("id") Integer id, HttpServletRequest request) {

		return null;
	}
}
