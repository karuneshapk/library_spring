package com.mikhail.holub.dao;

import com.mikhail.holub.model.User;

import java.util.List;


public interface UserDao {

	User findById(int id);
	
	User findByNickName(String nickName);
	
	void save(User user);
	
	void deleteByNickName(String nickName);
	
	List<User> findAllUsers();

}

