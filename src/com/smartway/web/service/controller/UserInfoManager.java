package com.smartway.web.service.controller;

import java.io.IOException;
import java.util.Collection;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
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
	@Autowired
	GenericService userAuthService ;
	/*private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;*/
    
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getAllUserInfo() throws JsonParseException, JsonMappingException, JAXBException, IOException, ParseException{
		Collection<UserInfo> userInfos = (Collection<UserInfo>) userInfoService.getAll();
		UserInfoMaster userinfomaster = new UserInfoMaster();
		userinfomaster.setDataList(userInfos);
		//Common common = new Common();
		return 	new Common().pojo2Json(userinfomaster);
	}
	
	
	
}

