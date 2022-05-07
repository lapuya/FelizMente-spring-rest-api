package es.ite.felizmente.user.model.persistence;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.ite.felizmente.user.model.entity.Admin;
import org.springframework.stereotype.Component;

import es.ite.felizmente.user.model.entity.User;

/*
 * THIS CLASS USES JPA TO MANAGE DATA IN THE DATABASE
 */

@Component
public class UserDao {
	private EntityManager em;
	
	private boolean openConnection(){
		try {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("felizmente");
			em = factory.createEntityManager();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean closeConnection(){
		try {
			em.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public User insert(User u) {
		if(!openConnection()) {
			return null;
		}
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(u);
		et.commit();
		closeConnection();
		return search(u.getEmail()); //if not null, inserted correctly
	}
	
	public User update(User u) {
		if(!openConnection()) {
			return null;
		}
		EntityTransaction et = em.getTransaction();
		et.begin();
		u = em.merge(u);
		et.commit();
		closeConnection();
		return u;
	}

	public boolean delete(int id) {
		if(!openConnection()) {
			return false;
		}
		try {
			User uAux = searchById(id);
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.remove(uAux);
			et.commit();
			closeConnection();
			return true; //if null, then we deleted it
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User search(String email) {
		if(!openConnection()) {
			return null;
		}
		try {
			return (em.createQuery("select user from User user where user.email = :email", User.class)
					.setParameter("email", email)
					.getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("More than 1 result");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> list() {
		if(!openConnection()) {
			return null;
		}		
		Query query = em.createQuery("select user from User user");
		List<User> usersList = query.getResultList();
		return usersList;
	}

	public User searchForLogin(String token) {
		if (!openConnection()) {
			return null;
		}
		String string = token;
		String[] parts = string.split("-");
		String email = parts[0];
		String password = parts[1];
		try {
			return (em.createQuery("select user from User user" +
							" where user.email = :email and user.password = :password", User.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getSingleResult());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//search by Id
	public User searchById(int id) {
		if(!openConnection()) {
			return null;
		}
		return em.find(User.class, id);
	}
}
