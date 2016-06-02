package com.mikhail.holub.service;

import com.mikhail.holub.model.User;

import java.util.List;


public interface UserService {
	
	User findById(int id);
	
	User findByNickName(String nickName);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByNickName(String nickName);

	List<User> findAllUsers(); 
	
	boolean isUserNickNameUnique(Integer id, String nickName);

}