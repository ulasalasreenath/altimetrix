package altimetrix.service;

import java.util.List;

import altimetrix.entities.User;

public interface UserService {

	List<User> getUsers();
	void saveUser(User user);
	User getUser(String username);
	void deleteUser(String username);

}
