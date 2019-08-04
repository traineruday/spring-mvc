package com.examples.form.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.examples.form.model.User;

@Service("userService")
public class UserService {

	Map<Integer, User> usersList = new HashMap<>();

	public UserService() {
		User user1 = new User();
		user1.setAddress("Hyd");
		user1.setEmail("user1@mail.com");
		user1.setId(1);
		user1.setName("User 1");
		usersList.put(1, user1);

		User user2 = new User();
		user2.setAddress("Hyd");
		user2.setEmail("user2@mail.com");
		user2.setId(2);
		user2.setName("User 2");
		usersList.put(2, user2);
	}

	public User findById(Integer id) {
		return usersList.get(id);
	}

	public List<User> findAll() {
		return new ArrayList<>(usersList.values());
	}

	public void save(User user) {
		usersList.put(user.getId(), user);
	}

	public void update(User user) throws Exception {
		if (usersList.containsKey(user.getId())) {
			usersList.put(user.getId(), user);
		} else {
			throw new Exception("User doesn't exist");
		}
	}

	public void delete(int id) {
		for (Iterator<Integer> iterator = usersList.keySet().iterator(); iterator.hasNext();) {
			Integer key = iterator.next();
			if (key == id) {
				iterator.remove();
			}
		}
	}

	public void saveOrUpdate(User user) throws Exception {
		if (user.isNew()) {
			user.setId(usersList.size()+1);
			save(user);
		} else {
			update(user);
		}
		
	}

}