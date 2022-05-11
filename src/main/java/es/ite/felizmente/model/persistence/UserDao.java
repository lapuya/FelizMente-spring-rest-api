package es.ite.felizmente.model.persistence;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.ite.felizmente.model.entity.Admin;
import lombok.Data;
import org.springframework.stereotype.Component;

import es.ite.felizmente.model.entity.User;

import java.util.Base64;
import java.util.List;

/*
 * THIS CLASS USES JPA TO MANAGE DATA IN THE DATABASE
 */

@Data
@Component
public class UserDao {
	private EntityManager em;
	Cipher encryptor;
	SecretKey scytale;


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
		try {
			u.setPassword(Base64.getEncoder().encodeToString(encryptor.doFinal(u.getPassword().getBytes())));
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(u);
			et.commit();
			closeConnection();
			return search(u.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

	public User searchByUser(User user) {
		if (!openConnection()) {
			return null;
		}

		String username = user.getUsername();

		User uAux = em.createQuery("select user from User user where user.email = :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
		try {
			if (uAux != null) {
				encryptor.init(Cipher.DECRYPT_MODE, scytale);
				byte [] passwordBytes = encryptor.doFinal(Base64.getDecoder().decode(uAux.getPassword()));
				String passwordString = new String (passwordBytes);
				if (user.getPassword().equals(passwordString)) {
					return uAux;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
}