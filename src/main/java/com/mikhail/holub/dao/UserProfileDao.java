package com.mikhail.holub.dao;

import java.util.List;

import com.mikhail.holub.model.UserProfile;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
