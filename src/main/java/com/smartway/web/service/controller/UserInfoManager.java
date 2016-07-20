package com.smartway.web.service.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartway.core.managebeans.UserInfoMaster;
import com.smartway.core.model.UserAuth;
import com.smartway.core.model.UserInfo;
import com.smartway.core.mysql.service.GenericService;
import com.smartway.core.utils.Common;
import com.smartway.core.utils.jdbcConnection;
import com.smartway.web.common.GenerateUIPojo;



@Controller
@RequestMapping(value="/userinfo")
public class UserInfoManager {
	
	private static Logger logger = Logger.getLogger(UserInfoManager.class);
	@Autowired
	GenericService userInfoService ;
	GenericService userAuthService ;
	/*private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;*/
	JSONObject status = new JSONObject();
    
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getAllUserInfo(){
		try{
			logger.debug("Started fetching all user data");
			Collection<UserInfo> userInfos = (Collection<UserInfo>) userInfoService.getAll();
			List<UserInfo> listOfObject = new ArrayList<UserInfo>();
	        for (UserInfo object : userInfos) {
				listOfObject.add(new GenerateUIPojo().setUserInfoAndAuth(object));
			}
			UserInfoMaster userinfomaster = new UserInfoMaster();
			userinfomaster.setDataList(listOfObject);
			JSONObject outputJson=(new Common()).pojo2Json(listOfObject);
			logger.debug("End of the method getAllUserInfo() ");
			return outputJson;
		}catch(Exception e){
			logger.error(e.getMessage());
			status.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
			return status;
		}
		
	}
	
	@RequestMapping(value="/{username}", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getUserInfo(@PathVariable("username") String username ){
		
		return null;
	}
	
	
	
}
