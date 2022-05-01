package es.ite.felizmente.user.model.persistence;

import es.ite.felizmente.user.model.entity.Admin;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.criteria.ParameterExpression;
import java.util.List;

@Component
public class AdminDao {

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

    public Admin insert(Admin a) {
        if(!openConnection()) {
            return null;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(a);
        et.commit();
        closeConnection();
        return a;
    }

    public Admin search(String token) {
        if (!openConnection()) {
            return null;
        }

        String string = token;
        String[] parts = string.split("-");
        String username = parts[0];
        String password = parts[1];
        try {
            return (em.createQuery("select admin from Admin admin" +
                            " where admin.username = :username and admin.password = :password", Admin.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
