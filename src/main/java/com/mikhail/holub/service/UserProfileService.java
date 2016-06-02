package com.mikhail.holub.service;

import java.util.List;

import com.mikhail.holub.model.UserProfile;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}
