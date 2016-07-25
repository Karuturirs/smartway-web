package com.smartway.web.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.smartway.core.model.UserAuth;
import com.smartway.core.model.UserInfo;
import com.smartway.web.service.controller.UserInfoManager;

public class GenerateUIPojo {
	private static Logger logger = Logger.getLogger(GenerateUIPojo.class);
	
	
	public UserInfo setUserInfo(UserInfo object ){
		UserInfo user =  new UserInfo();
		user.setUserId(object.getUserId());
		user.setFirstName(object.getFirstName());
		user.setLastName(object.getLastName());
		user.setBirthDate(object.getBirthDate());
		user.setEmail(object.getEmail());
		user.setGender(object.getGender());
		user.setPhone(object.getPhone());
		user.setUpdTs(object.getUpdTs());
		return user;
	}
	public UserAuth setUserAuth(UserAuth object ){
		UserAuth userAuth = new UserAuth();
		userAuth.setUserName(object.getUserName());
		//userAuth.setPassword(userauth.getPassword());
		userAuth.setUpdTs(object.getUpdTs());
		return userAuth;
	}
	
	public UserInfo setUserInfoAndAuth(UserInfo object ){
		logger.debug(" started setting USERINFO and Authentication details of id::"+object.getUserId());
		UserInfo user = setUserInfo(object);
		List<UserAuth> listOfauth = new ArrayList<UserAuth>();
		for (UserAuth userauth : object.getUserAuths()) {
			listOfauth.add(setUserAuth(userauth));
		}
		user.setUserAuths(listOfauth);
		logger.debug(" completed setting USERINFO and Authentication of "+object.getUserId());
		return user;
	}
	
	public UserAuth setUserAuthAndInfo(UserAuth object){
		logger.debug(" started setting USERINFO and Authentication details of id::"+object.getUserName());
		UserAuth userauth = setUserAuth(object);
		UserInfo userinfo = object.getUserInfo();
		UserInfo user = setUserInfo(userinfo);
		userauth.setUserInfo(user);
		logger.debug(" completed setting USERINFO and Authentication of "+object.getUserName());
		return userauth;
	}
}
