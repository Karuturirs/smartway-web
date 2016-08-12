package com.smartway.web.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.smartway.core.model.ListUserDevice;
import com.smartway.core.model.UserAuth;
import com.smartway.core.model.UserInfo;

public class GenerateUIPojo {
	private static Logger logger = Logger.getLogger(GenerateUIPojo.class);
	
	
	public UserInfo createUserInfo(UserInfo object){
		UserInfo user =  new UserInfo();
		user.setUserId(object.getUserId());
		user.setFirstName(object.getFirstName());
		user.setLastName(object.getLastName());
		user.setBirthDate(object.getBirthDate());
		user.setEmail(object.getEmail());
		user.setGender(object.getGender());
		user.setPhone(object.getPhone());
		user.setUpdTs(object.getUpdTs());
		return user;
	}
	public UserAuth createUserAuth(UserAuth object ){
		UserAuth userAuth = new UserAuth();
		userAuth.setUserId(object.getUserId());
		userAuth.setUserName(object.getUserName());
		//userAuth.setPassword(userauth.getPassword());
		userAuth.setUpdTs(object.getUpdTs());
		return userAuth;
	}
	
	public ListUserDevice createUserDevice(ListUserDevice object ){
		ListUserDevice userdevice = new ListUserDevice();
		if(object.getItemId()!=null)userdevice.setItemId(object.getItemId());
		if(object.getItemName()!=null)userdevice.setItemName(object.getItemName());
		if(object.getItemState()!=null)userdevice.setItemState(object.getItemState());
		if(object.getItemDesc()!=null)userdevice.setItemDesc(object.getItemDesc());
		userdevice.setId(object.getId());
		if(object.getCol1()!=null)userdevice.setCol1(object.getCol1());
		if(object.getCol2()!=null)userdevice.setCol2(object.getCol2());
		if(object.getCol3()!=null)userdevice.setCol3(object.getCol3());
		if(object.getCol4()!=null)userdevice.setCol4(object.getCol4());
		if(object.getCol5()!=null)userdevice.setCol5(object.getCol5());
		if(object.getCol6()!=null)userdevice.setCol6(object.getCol6());
		if(object.getCol7()!=null)userdevice.setCol7(object.getCol7());
		if(object.getCol8()!=null)userdevice.setCol8(object.getCol8());
		if(object.getCol9()!=null)userdevice.setCol9(object.getCol9());
		if(object.getCol10()!=null)userdevice.setCol10(object.getCol10());
		
		if(object.getCol1type()!=null)userdevice.setCol1type(object.getCol1type());
		if(object.getCol2type()!=null)userdevice.setCol2type(object.getCol2type());
		if(object.getCol3type()!=null)userdevice.setCol3type(object.getCol3type());
		if(object.getCol4type()!=null)userdevice.setCol4type(object.getCol4type());
		if(object.getCol5type()!=null)userdevice.setCol5type(object.getCol5type());
		if(object.getCol6type()!=null)userdevice.setCol6type(object.getCol6type());
		if(object.getCol7type()!=null)userdevice.setCol7type(object.getCol7type());
		if(object.getCol8type()!=null)userdevice.setCol8type(object.getCol8type());
		if(object.getCol9type()!=null)userdevice.setCol9type(object.getCol9type());
		if(object.getCol10type()!=null)userdevice.setCol10type(object.getCol10type());
		userdevice.setUpdTs(object.getUpdTs());
		
		return userdevice;
	}
	
	/*public ListUserDevices setUserDeviceObject(Object[] object ){
		
		for (Object object2 : object) {
			
		}
	 
		return 
	}*/
	
	public UserInfo createUserInfoAuthAndDevice(UserInfo object ){
		logger.debug(" started setting USERINFO and Authentication details of id::"+object.getUserId());
		UserInfo user = createUserInfo(object);
		user.setUserAuth(createUserAuth(object.getUserAuth()));
		
		List<ListUserDevice> listuserdevices = new ArrayList<ListUserDevice>();
		for (ListUserDevice userdevice : object.getListUserDevices()) {
			listuserdevices.add(createUserDevice(userdevice));
		}
		user.setListUserDevices(listuserdevices);
		logger.debug(" completed setting USERINFO and authentication of "+object.getUserId());
		return user;
	}
	
	public UserAuth setUserAuthAndInfo(UserAuth object){
		logger.debug(" started setting USERINFO and Authentication details of id::"+object.getUserName());
		UserAuth userauth = createUserAuth(object);
		UserInfo userinfo = object.getUserInfo();
		UserInfo user = createUserInfo(userinfo);
		userauth.setUserInfo(user);
		logger.debug(" completed setting USERINFO and Authentication of "+object.getUserName());
		return userauth;
	}
}
