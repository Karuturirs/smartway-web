package com.smartway.web.service.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartway.core.model.DevicesData;
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
	
	private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
	JSONObject status = new JSONObject();
    
	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getAllUserInfo(){
		try{
			logger.debug("Started fetching all user data");
			Collection<UserInfo> userInfos = (Collection<UserInfo>) userInfoService.getAll();
			List<UserInfo> listOfObject = new ArrayList<UserInfo>();
	        for (UserInfo object : userInfos) {
				listOfObject.add(new GenerateUIPojo().setUserInfoAuthAndDevice(object));
			}
			
			JSONObject outputJson=(new Common()).pojo2Json(listOfObject,"list");
			logger.debug("End of the method getAllUserInfo() ");
			//GenerateID g= new GenerateID();
			//System.out.println(g.generateNextID());
			return outputJson;
		}catch(Exception e){
			logger.error(e.getMessage());
			status.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
			return status;
		}
		
	}
	
	@RequestMapping(value="/{username}", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject getUserInfo(@PathVariable("username") String username ) throws JsonParseException, JsonMappingException, JAXBException, IOException, ParseException{
		
		try{
			logger.debug("Started fetching user data:"+username);
			System.out.println("----->");
			JSONObject job = new JSONObject();
			Collection<UserAuth> userAuth =  userAuthService.findByHSQLQuery("from UserAuth where userName='"+username+"'");
				for (UserAuth userAuth2 : userAuth) {
					UserAuth userth = new GenerateUIPojo().setUserAuthAndInfo(userAuth2);
					job = (new Common()).pojo2Json(userth);
				}
				logger.debug("Completed fetching user data:"+username);
				return job;
			
		}catch(Exception e){
			logger.error(e.getMessage());
			status.put("ERROR", e.getMessage().toString());
			e.printStackTrace();
			return status;
		}
	}
	
	@RequestMapping(value="/validate",method = RequestMethod.POST,headers = "Content-type=application/json")
	public @ResponseBody JSONObject getUserInfo(@RequestBody JSONObject jsonObject){
		logger.debug("Started fetching user validation");
		JSONObject job = new JSONObject();
		Collection<UserAuth> userAuth =  userAuthService.findByHSQLQuery("from UserAuth where userName='"+jsonObject.get("username")+"'");
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
			return job;
		}else{
			logger.info("No record found with the username:"+jsonObject.get("username"));
			status.put("Error", "No such Username found in the records");
			return status;
		}
		
	}
	
	@RequestMapping(value="/{username}/adddevice", method = RequestMethod.POST, produces= "application/json")
	public @ResponseBody JSONObject addDeviceToUser(@PathVariable("username") String username,@RequestBody JSONObject jsonObject ) {
		
		gson = gsonBuilder.create();
		try {
			ListUserDevice ldevice = gson.fromJson(jsonObject.toJSONString(), ListUserDevice.class);
			ldevice.setItemId(GenerateID.getInstance().generateNextID());
			System.out.println(ldevice.getCol1());
			listUserDevicesService.save(ldevice);
			//jsonObject=updateRecord(editInput);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	
	@RequestMapping(value="/{username}/listdevices", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject listDeviceOfUser(@PathVariable("username") String username ) {
		logger.debug("Started fetching list of all devices of user:"+username);
		JSONObject job = new JSONObject();
		job.put("Button", "Add Devices");
		job.put("url", "/"+username+"/adddevice");
		Collection<Object[]> lUserDevices = listUserDevicesService.findBySQLQuery("select "
				+ "lud.ID, lud.USER_ID, lud.ITEM_ID, lud.ITEM_NAME, lud.ITEM_STATE, lud.ITEM_DESC "
				+ "from LIST_USER_DEVICES lud"
				+ " Left JOIN USER_AUTH ua ON  ua.USER_ID =lud.ID "
				+ "where ua.USER_NAME ='"+username+"'");
		
		if(lUserDevices.size()!=0){
			for (Object[] listUserDevice : lUserDevices) {
				logger.debug("device name"+listUserDevice[0]);
			}
			logger.debug("device name"+lUserDevices);
		}
		return job;
	}
	
	
	
}

