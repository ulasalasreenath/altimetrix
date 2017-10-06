package altimetrix.dao;

import java.util.List;

import altimetrix.entities.User;

public interface UserDAO {

	// returns list of users
	List<User> getUsers();

	// saves the give user
	void saveUser(User user);

	// gets the user based on user name
	User getUser(String userName);

	// deletes the user based on user name
	void deleteUser(String userName);

}
