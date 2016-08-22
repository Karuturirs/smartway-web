package com.smartway.web.service.controller;

import java.awt.Window.Type;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

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
import com.smartway.core.model.DevicesData;
import com.smartway.core.model.ListUserDevice;
import com.smartway.core.mysql.service.GenericService;
import com.smartway.core.utils.Common;
import com.smartway.web.common.CoreDataBeans;
import com.smartway.web.common.DataManager;
import com.smartway.web.common.GenerateUIPojo;


@Controller
@RequestMapping(value="/deviceinfo")
public class DeviceInfoManager {
	
	private static Logger logger = Logger.getLogger(DeviceInfoManager.class);
	private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
	
	@Autowired
	GenericService listUserDevicesService;
	@Autowired
	GenericService devicesDataService;
	
	@RequestMapping(value="/{deviceid}/info", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject deviceInfo(@PathVariable("deviceid") String deviceid ) {
		logger.debug("Started fetching devices info::"+deviceid);
		JSONObject job = new JSONObject();
		job.put("url", "/"+deviceid+"/info");
		Collection<ListUserDevice> lUserDevicesInfo = listUserDevicesService.findByHSQLQuery(
				"from ListUserDevice lud where lud.itemId='"+deviceid+"'");
		for (ListUserDevice listUserDevice : lUserDevicesInfo) {
			ListUserDevice lud = new  GenerateUIPojo().createUserDevice(listUserDevice);
			job.put("deviceinfo",(new Common()).pojo2JsonObject(lud));
		}
		
		return job;
	}
	
	@RequestMapping(value="/{deviceid}/data", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject showDeviceData(@PathVariable("deviceid") String deviceid ) {
		logger.debug("Started fetching devices data::"+deviceid);
		
		JSONObject deviceinfo = deviceInfo(deviceid);
		GenerateUIPojo gup = new GenerateUIPojo();
		DataManager dm =  gup.createDataManager(deviceinfo);
		
		logger.debug(deviceinfo.get("deviceinfo"));
		Collection<DevicesData> lUserDevices = devicesDataService.findByHSQLQuery("from DevicesData dd "
				+ "where dd.listUserDevice.itemId ='"+deviceid+"'");
		for (DevicesData devicesData : lUserDevices) {
			 gup.manageCoreDataBean(devicesData,dm);		
		}
		//logger.debug((new Common()).pojo2JsonObject(dm));
		JSONObject jo =(new Common()).pojo2JsonObject(dm);
		jo.put("url", "/"+deviceid+"/data");
		return jo;
	}
	
	@RequestMapping(value="/{deviceid}/datainto", method = RequestMethod.POST,headers = "Content-type=application/json")
	public @ResponseBody JSONObject addDeviceData(@PathVariable("deviceid") String deviceid,@RequestBody JSONObject jsonObject ) {
		logger.debug("Started Inserting device id::"+deviceid+" data "+jsonObject.toJSONString());
		JSONObject job = new JSONObject();
		job.put("url", "/"+deviceid+"/datainto");
		gson = gsonBuilder.create();
		try {
			DevicesData devicesData = gson.fromJson(jsonObject.toString(), DevicesData.class);
			devicesDataService.save(devicesData);
			job.put("Message", "Successfully inserted the data  into device id::"+deviceid);
		}catch (Exception e) {
			logger.error("No able to insert data for device id::"+deviceid+"::"+e.getStackTrace());
			job.put("ERROR", "No able to insert data for device id::"+deviceid+"::"+e.getMessage().toString());
			e.printStackTrace();
			
		}
		return job;
	}

}
