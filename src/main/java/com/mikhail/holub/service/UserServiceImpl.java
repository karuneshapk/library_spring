package com.mikhail.holub.service;

import java.util.List;

import com.mikhail.holub.dao.UserDao;
import com.mikhail.holub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByNickName(String nickName) {
		User user = dao.findByNickName(nickName);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			entity.setNickNameId(user.getNickNameId());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setUserProfiles(user.getUserProfiles());
		}
	}

	
	public void deleteUserByNickName(String nickName) {
		dao.deleteByNickName(nickName);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public boolean isUserNickNameUnique(Integer id, String nickName) {
		User user = findByNickName(nickName);
		return ( user == null || ((id != null) && (user.getId() == id)));
	}
	
}
