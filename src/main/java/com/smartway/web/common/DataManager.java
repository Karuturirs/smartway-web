package com.smartway.web.common;

import java.util.Collection;
import java.util.HashMap;

public class DataManager<T> {
	
	HashMap<String,T> cobj;
	String deviceId;
	String deviceName;
	
		
	public HashMap<String, T> getCobj() {
		return cobj;
	}
	public void setCobj(HashMap<String, T> cobj) {
		this.cobj = cobj;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	

}
