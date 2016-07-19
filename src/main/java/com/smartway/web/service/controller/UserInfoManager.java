package com.smartway.web.service.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartway.core.managebeans.UserInfoMaster;
import com.smartway.core.model.UserAuth;
import com.smartway.core.model.UserInfo;
import com.smartway.core.mysql.service.GenericService;
import com.smartway.core.utils.Common;



@Controller
public class UserInfoManager {
	
	@Autowired
	GenericService userInfoService ;
	GenericService userAuthService ;
	/*private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;*/
	JSONObject error = new JSONObject();
    
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getAllUserInfo(){
		try{
			System.out.println("-$-----");
			Collection<UserInfo> userInfos = (Collection<UserInfo>) userInfoService.getAll();
			List<UserInfo> listOfObject = new ArrayList<UserInfo>();
	        for (UserInfo object : userInfos) {
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
				listOfObject.add(user);
			}
			UserInfoMaster userinfomaster = new UserInfoMaster();
			System.out.println("################################## "+listOfObject.size());
			
			userinfomaster.setDataList(listOfObject);
			//Common common = new Common();
			JSONObject outputJson=(new Common()).pojo2Json(listOfObject);
			return outputJson;
		}catch(Exception e){
			System.out.println("--"+e.getMessage());
			error.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
			return error;
		}
		
	}
	
	
	
}

