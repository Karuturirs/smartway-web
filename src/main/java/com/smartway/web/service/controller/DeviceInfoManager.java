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
		JSONObject job = new JSONObject();
		job.put("url", "/"+deviceid+"/data");
		JSONObject deviceinfo = deviceInfo(deviceid);
		
		DataManager<CoreDataBeans> dm= new DataManager<CoreDataBeans>();
		JSONObject jo = (JSONObject) deviceinfo.get("deviceinfo");
		dm.setDeviceId(jo.get("itemId").toString());
		dm.setDeviceName(jo.get("itemName").toString());
		dm.setCobj(new HashMap<String, CoreDataBeans>());
		
		if(jo.get("col1")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col1").toString());
			String[] type =jo.get("col1type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col1",cc);	
		}
		if(jo.get("col2")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col2").toString());
			String[] type =jo.get("col2type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col2",cc);	
		}
		if(jo.get("col3")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col3").toString());
			String[] type =jo.get("col3type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col3",cc);	
		}
		if(jo.get("col4")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col4").toString());
			String[] type =jo.get("col4type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col4",cc);	
		}
		if(jo.get("col5")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col5").toString());
			String[] type =jo.get("col5type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col5",cc);	
		}
		if(jo.get("col6")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col6").toString());
			String[] type =jo.get("col6type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col6",cc);	
		}
		if(jo.get("col7")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col7").toString());
			String[] type =jo.get("col7type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col7",cc);	
		}
		if(jo.get("col8")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col8").toString());
			String[] type =jo.get("col8type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col8",cc);	
		}
		if(jo.get("col9")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col9").toString());
			String[] type =jo.get("col9type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col9",cc);	
		}
		if(jo.get("col10")!=null){
			CoreDataBeans<Timestamp, String> cc= new CoreDataBeans<Timestamp, String>();
			cc.setParam(jo.get("col10").toString());
			String[] type =jo.get("col10type").toString().split(":");
			cc.setTypeofparam(type[0]);
			cc.setScaleofparam(type[1]);
			cc.setMapcontent(new HashMap<Timestamp, String>());
			dm.getCobj().put("col10",cc);	
		}
		
		
		
		logger.debug(deviceinfo.get("deviceinfo"));
		Collection<DevicesData> lUserDevices = devicesDataService.findByHSQLQuery("from DevicesData dd "
				+ "where dd.listUserDevice.itemId ='"+deviceid+"'");
		for (DevicesData devicesData : lUserDevices) {
		 
			if(devicesData.getCol1()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col1");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol1());
			}
			if(devicesData.getCol2()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col2");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol2());
			}
			if(devicesData.getCol3()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col3");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol3());
			}
			if(devicesData.getCol4()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col4");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol4());
			}
			if(devicesData.getCol5()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col5");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol5());
			}
			if(devicesData.getCol6()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col6");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol6());
			}
			if(devicesData.getCol7()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col7");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol7());
			}
			if(devicesData.getCol8()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col8");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol8());
			}
			if(devicesData.getCol9()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col9");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol9());
			}
			if(devicesData.getCol10()!=null){
				CoreDataBeans<Timestamp, String> cc =dm.getCobj().get("col10");
				cc.getMapcontent().put(devicesData.getUpdTs(), devicesData.getCol10());
			}
			
		}
		logger.debug((new Common()).pojo2JsonObject(dm));
		return (new Common()).pojo2JsonObject(dm);
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
