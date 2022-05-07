package es.ite.felizmente.user.model.persistence;

import es.ite.felizmente.user.model.entity.Admin;
import org.springframework.stereotype.Component;

import javax.persistence.*;

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

    public Admin search(Admin a) {
        if (!openConnection()) {
            return null;
        }

        String username = a.getUsername();
        String password = a.getPassword();
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
