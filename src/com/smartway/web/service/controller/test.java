package com.smartway.web.service.controller;

import java.util.Collection;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartway.core.managebeans.UserInfoMaster;
import com.smartway.core.model.UserInfo;
import com.smartway.core.mysql.service.GenericService;
import com.smartway.core.utils.Common;

public class test {

	@Autowired
	static GenericService userInfoService ;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			System.out.println("-$-----"+userInfoService);
			Collection<UserInfo> userInfos = userInfoService.findByHSQLQuery("from USER_INFO");
			UserInfoMaster userinfomaster = new UserInfoMaster();
			System.out.println(">>>"+userInfos.size());
			
			userinfomaster.setDataList(userInfos);
			//Common common = new Common();
			JSONObject outputJson=(new Common()).pojo2Json(userinfomaster);
			
		}catch(Exception e){
			System.out.println("--"+e.getMessage());
			e.printStackTrace();
		}
	}

}
