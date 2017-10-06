package altimetrix.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import altimetrix.entities.User;
import altimetrix.exceptions.UserNotFoundException;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private EntityManager em;

	@Override
	public List<User> getUsers() {

		TypedQuery<User> query = em.createQuery("from User order by userName", User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public void saveUser(User user) {
		em.persist(user);
	}

	@Override
	public User getUser(String userName) {
		try
		{
			TypedQuery<User> getQuery = em.createQuery("from User where userName =:un", User.class);
			getQuery.setParameter("un", userName);	
			return getQuery.getSingleResult();
		} catch(NoResultException noResult)
		{
			return null;
		}
	}

	@Override
	public void deleteUser(String userName) {
		User user = getUser(userName);
		if(user != null) {
			em.remove(user);
		} else
		{
			throw new UserNotFoundException("User not found with the given user name " + userName);
		}
	}

}
