package com.smartway.web.service.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartway.core.model.ListUserDevice;
import com.smartway.core.model.UserAuth;
import com.smartway.core.model.UserInfo;
import com.smartway.core.mysql.service.GenericService;
import com.smartway.core.utils.Common;
import com.smartway.core.utils.GenerateID;
import com.smartway.web.common.GenerateUIPojo;



@Controller
@RequestMapping(value="/userinfo")
public class UserInfoManager {
	
	private static Logger logger = Logger.getLogger(UserInfoManager.class);
	@Autowired
	GenericService userInfoService ;
	@Autowired
	GenericService userAuthService ;
	@Autowired
	GenericService listUserDevicesService ;
	@Autowired
	GenericService genItemIdService;
	
	private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
	JSONObject status = new JSONObject();
    
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getAllUserInfo(){
		JSONObject job = new JSONObject();
		job.put("url", "/userinfo/getall");
		try{
			logger.debug("Started fetching all user data");
			Collection<UserInfo> userInfos = (Collection<UserInfo>) userInfoService.getAll();
			List<UserInfo> listOfObject = new ArrayList<UserInfo>();
	        for (UserInfo object : userInfos) {
				listOfObject.add(new GenerateUIPojo().createUserInfoAuthAndDevice(object));
			}
	        job.put("list", (new Common()).pojo2JsonArray(listOfObject));
			logger.debug("End of the method getAllUserInfo() ");
			
		}catch(Exception e){
			logger.error(e.getMessage());
			job.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
		}
		return job;
		
	}
	
	@RequestMapping(value="/{username}", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getUserInfo(@PathVariable("username") String username ) {
		JSONObject job = new JSONObject();
		job.put("url", "/"+username);
		try{
			logger.debug("Started fetching user data:"+username);
			Collection<UserAuth> userAuth =  userAuthService.findByHSQLQuery(
					"from UserAuth "
					+ "where userName='"+username+"'");
				for (UserAuth userAuth2 : userAuth) {
					UserAuth userth = new GenerateUIPojo().setUserAuthAndInfo(userAuth2);
					job = (new Common()).pojo2JsonObject(userth);
				}
				logger.debug("Completed fetching user data:"+username);	
			
		}catch(Exception e){
			logger.error(e.getMessage());
			job.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
		}
		return job;
	}
	
	
	@RequestMapping(value="/register", method = RequestMethod.POST,headers = "Content-type=application/json")
	public @ResponseBody JSONObject regiserUser(@RequestBody JSONObject jsonObject) {
		JSONObject job = new JSONObject();
		job.put("url", "/userinfo/register");
		gson = gsonBuilder.setDateFormat("yyyy-MM-dd").create();
		try {
			UserInfo userInfo = gson.fromJson(jsonObject.toString(), UserInfo.class);
			userInfo.setCrtDt(new Date());
			
			userInfoService.save(userInfo);
			userAuthService.save(userInfo.getUserAuth());
			//jsonObject=updateRecord(editInput);
			job.put("Message", "Succesfully registered the user::"+userInfo.getUserName());
		}catch (Exception e) {
			logger.error("No able to register the user::"+e.getStackTrace());
			job.put("ERROR", "No able to register the device::"+e.getMessage().toString());
			e.printStackTrace();
			
		}
		return job;
	}
	@RequestMapping(value="/{id}/removeuser", method = RequestMethod.POST,produces= "application/json")
	public @ResponseBody JSONObject removeUser(@PathVariable("id") String userid) {
		
		logger.info("Processing to remove the userid:"+userid);
		JSONObject job = new JSONObject();
		job.put("url", "/userinfo/"+userid+"/removeuser");
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(Integer.parseInt(userid));
			userInfo.setUserName("sanka");
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("urid", Integer.parseInt(userid));
			userInfoService.deleteByHSQLQuery("delete from UserInfo where userId=:urid", map );
		//	userAuthService.deleteByHSQLQuery("delete from UserInfo where userId=:urid", map)
			job.put("Message", "Successfully deleted userid::"+userid);
		}catch (Exception e) {
			logger.error("No able to delete the userid:"+userid+" : "+e.getStackTrace());
			job.put("ERROR", "No able to delete the userid:"+userid+" : "+e.getStackTrace());
			e.printStackTrace();
		}
		return job;
	}
	
	@RequestMapping(value="/validate",method = RequestMethod.POST,headers = "Content-type=application/json")
	public @ResponseBody JSONObject getUserInfo(@RequestBody JSONObject jsonObject){
		logger.debug("Started fetching user validation");
		JSONObject job = new JSONObject();
		job.put("url", "/userinfo/validate");
		Collection<UserAuth> userAuth =  userAuthService.findByHSQLQuery(
				"from UserAuth "
				+ "where userName='"+jsonObject.get("username")+"'");
		if(userAuth.size()!=0){
			for (UserAuth userAuth2 : userAuth) {
				System.out.println(userAuth2.getPassword());
				logger.info(userAuth2.getPassword());
				if(userAuth2.getPassword().equals(jsonObject.get("password"))){
					job.put("state",true);
					job.put("success", "Successful login");
				}else{
					job.put("state",false);
					job.put("ERROR", "Username or Password not matched with the records.");
				}
			}
			logger.debug("Completed fetching user validation:");
			
		}else{
			logger.info("No record found with the username:"+jsonObject.get("username"));
			job.put("ERROR", "No such Username found in the records");
		}
		return job;
	}
	
	@RequestMapping(value="/{username}/adddevice", method = RequestMethod.POST, produces= "application/json")
	public @ResponseBody JSONObject addDeviceToUser(@PathVariable("username") String username,@RequestBody JSONObject jsonObject ) {
		JSONObject job = new JSONObject();
		job.put("url", "/userinfo/"+username+"/adddevice");
		gson = gsonBuilder.create();
		try {
			ListUserDevice ldevice = gson.fromJson(jsonObject.toString(), ListUserDevice.class);
			UserInfo userInfo = new UserInfo();
			
			userInfo.setUserName(username);
			//userInfo.setUserAuths(listuserAuth);
			ldevice.setUserInfo(userInfo);
			ldevice.setCrtDt(new Date());
			ldevice.setItemId(GenerateID.getInstance().generateNextID(genItemIdService));
			System.out.println(ldevice.getCol1());
			listUserDevicesService.save(ldevice);
			logger.info("Added the device :: "+ldevice.getItemId()+" by "+ldevice.getUserInfo().getUserName());
			job.put("Message", "Successfully registered the device id ::"+ldevice.getItemId()+" by "+ldevice.getUserInfo().getUserName());
			//jsonObject=updateRecord(editInput);
		} catch (Exception e) {
			logger.error("No able to register the device::"+e.getStackTrace());
			job.put("ERROR", "No able to register the device::"+e.getMessage().toString());
		}
		return job;
		
	}
	
	@RequestMapping(value="/{username}/listdevices", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject listDeviceOfUser(@PathVariable("username") String username ) {
		logger.debug("Started fetching list of all devices of user:"+username);
		JSONObject job = new JSONObject();
		
		job.put("Button", "Add Devices");
		job.put("url", "/userinfo/"+username+"/adddevice");
		
		Collection<ListUserDevice>  listdevices = listUserDevicesService.findByHSQLQuery(
				"select lud from ListUserDevice lud "
				+ "left join lud.userInfo us"
				+ " where us.userName = '"+username+"'");
		
		List<ListUserDevice> listOfObject = new ArrayList<ListUserDevice>();
		for (ListUserDevice listUserDevices : listdevices) {
			listOfObject.add(new GenerateUIPojo().createUserDevice(listUserDevices));
		}
		job.put("devices",(new Common()).pojo2JsonArray(listOfObject));
	
		return job;
	}
	
	
	
}

