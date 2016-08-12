package com.smartway.web.service.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartway.core.model.DevicesData;
import com.smartway.core.model.ListUserDevice;
import com.smartway.core.mysql.service.GenericService;


@Controller
@RequestMapping(value="/deviceinfo")
public class DeviceInfoManager {
	
	private static Logger logger = Logger.getLogger(DeviceInfoManager.class);
	
	@Autowired
	GenericService listUserDevicesService;
	@Autowired
	GenericService devicesDataService;
	
	@RequestMapping(value="/info/{deviceid}", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject deviceInfo(@PathVariable("deviceid") String deviceid ) {
		logger.debug("Started fetching devices info::"+deviceid);
		JSONObject job = new JSONObject();
		job.put("Button", "Add Devices");
		job.put("url", "/info/"+deviceid);
		Collection<ListUserDevice> lUserDevicesInfo = listUserDevicesService.findBySQLQuery("select *"
				+ "from LIST_USER_DEVICES lud"
				+ "where lud.itemId ='"+deviceid+"'");
		if(lUserDevicesInfo!=null && lUserDevicesInfo.size()!=0){
			for (ListUserDevice listUserDevice : lUserDevicesInfo) {
				logger.debug("device name"+listUserDevice.getItemId());
			}
			logger.debug("device name"+lUserDevicesInfo);
		}
		return job;
	}
	
	@RequestMapping(value="/data/{deviceid}", method = RequestMethod.GET, produces= "application/json")
	public @ResponseBody JSONObject deviceData(@PathVariable("deviceid") String deviceid ) {
		logger.debug("Started fetching devices info::"+deviceid);
		JSONObject job = new JSONObject();
		job.put("Button", "Add Devices");
		job.put("url", "/info/"+deviceid);
		Collection<DevicesData> lUserDevices = devicesDataService.findBySQLQuery("select *"
				+ "from DEVICES_DATA"
				+ "where ITEM_ID ='"+deviceid+"'");
		
		return job;
	}

}
