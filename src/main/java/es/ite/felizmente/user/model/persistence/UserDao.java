package es.ite.felizmente.user.model.persistence;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
		return search(u.getId()); //if not null, inserted correctly
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

	public User delete(int id) {
		if(!openConnection()) {
			return null;
		}

		User uAux = search(id);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(uAux);
		et.commit();
		closeConnection();
		return search(uAux.getId()); //if null, then we deleted it
	}
	
	public User search(int id) {
		if(!openConnection()) {
			return null;
		}
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> list() {
		if(!openConnection()) {
			return null;
		}		
		//para hacer la consulta debemos de usar JPQL
		Query query = em.createQuery("select user from User user");
		List<User> usersList = query.getResultList();
		return usersList;
	}

}
