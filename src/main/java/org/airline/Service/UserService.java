package org.airline.Service;

import org.airline.Entity.User;

public interface UserService {

	User saveData(User user);

	User getUserByUserName(String email);

	User findAdminAll();

}
