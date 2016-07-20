package com.smartway.web.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.smartway.core.model.UserAuth;
import com.smartway.core.model.UserInfo;
import com.smartway.web.service.controller.UserInfoManager;

public class GenerateUIPojo {
	private static Logger logger = Logger.getLogger(GenerateUIPojo.class);
	public UserInfo setUserInfoAndAuth(UserInfo object ){
		logger.debug(" started setting USERINFO and Authentication details of id::"+object.getUserId());
		UserInfo user =  new UserInfo();
		user.setUserId(object.getUserId());
		user.setFirstName(object.getFirstName());
		user.setLastName(object.getLastName());
		user.setBirthDate(object.getBirthDate());
		user.setEmail(object.getEmail());
		user.setGender(object.getGender());
		user.setPhone(object.getPhone());
		user.setUpdTs(object.getUpdTs());
		List<UserAuth> listOfauth = new ArrayList<UserAuth>();
		for (UserAuth userauth : object.getUserAuths()) {
			UserAuth userAuth = new UserAuth();
			userAuth.setUserName(userauth.getUserName());
			//userAuth.setPassword(userauth.getPassword());
			userAuth.setUpdTs(userauth.getUpdTs());
			listOfauth.add(userAuth);
		}
		user.setUserAuths(listOfauth);
		logger.debug(" completed setting USERINFO and Authentication of "+object.getUserId());
		return user;
	}
}
