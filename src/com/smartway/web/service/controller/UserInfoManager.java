package com.smartway.web.service.controller;

import java.util.Collection;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartway.core.managebeans.UserInfoMaster;
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
			UserInfoMaster userinfomaster = new UserInfoMaster();
			System.out.println(userInfos.size());
			
			userinfomaster.setDataList(userInfos);
			//Common common = new Common();
			JSONObject outputJson=(new Common()).pojo2Json(userinfomaster);
			return outputJson;
		}catch(Exception e){
			System.out.println("--"+e.getMessage());
			error.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
			return error;
		}
		
	}
	
	
	
}

