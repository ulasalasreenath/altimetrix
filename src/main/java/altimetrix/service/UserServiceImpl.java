package altimetrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import altimetrix.dao.UserDAO;
import altimetrix.entities.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDAO userDAO;

	@Override
	@Transactional
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Override
	public User getUser(String username) {
		return userDAO.getUser(username);
	}

	@Override
	@Transactional
	public void deleteUser(String username) {
		userDAO.deleteUser(username);
	}

}
