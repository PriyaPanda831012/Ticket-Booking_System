package org.airline.Service;

import java.util.List;

import org.airline.Dao.UserRepository;
import org.airline.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepositery;
	@Override
	public User saveData(User user) {
		return this.userRepositery.save(user);
		
	}
	@Override
	public User getUserByUserName(String email) {
		
		return this.userRepositery.getUserByUserName(email);
	}
	@Override
	public User findAdminAll() {
		List<User> admin=this.userRepositery.findAll();
		User user1=null;
		for (User user : admin) {
			if(user.getRole().equals("ROLE_ADMIN"))
			{
				user1=user;
				break;
			}
		}
		return user1;
	}

}
