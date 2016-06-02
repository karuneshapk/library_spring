package com.mikhail.holub.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable{
	MEMBER("MEMBER"),
	ADMIN("ADMIN");
	
	String userProfileType;
	
	private UserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
