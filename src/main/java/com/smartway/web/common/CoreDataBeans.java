package com.smartway.web.common;

import java.util.HashMap;

public class CoreDataBeans<K,V> {
	
	String param; //temp
	String typeofparam; //float
	String scaleofparam; //C
	HashMap<K,V> mapcontent;
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getTypeofparam() {
		return typeofparam;
	}
	public void setTypeofparam(String typeofparam) {
		this.typeofparam = typeofparam;
	}
	public String getScaleofparam() {
		return scaleofparam;
	}
	public void setScaleofparam(String scaleofparam) {
		this.scaleofparam = scaleofparam;
	}
	public HashMap<K, V> getMapcontent() {
		return mapcontent;
	}
	public void setMapcontent(HashMap<K, V> mapcontent) {
		this.mapcontent = mapcontent;
	}
	
	
	

}
