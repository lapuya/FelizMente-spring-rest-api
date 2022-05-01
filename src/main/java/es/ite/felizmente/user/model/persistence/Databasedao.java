package es.ite.felizmente.user.model.persistence;

import es.ite.felizmente.user.model.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
     1. We are aware that setting passwords this way is a huge breach of security. We are doing it in this manner
     for debugging purposes. Implement encryption.
     2. It is called "Databasedao" and not DataBaseDao because we had problems with naming convention in main class.
 */

@Data
@AllArgsConstructor
@Component
public class Databasedao {

    public boolean alreadyWithAdmins() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("felizmente");
        EntityManager em = factory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        long numRows = (long) em.createQuery("SELECT COUNT(a.id) FROM Admin a").getSingleResult();
        et.commit();
        em.close();
        factory.close();
        if (numRows > 0)
            return true;
        return false;
    }


    public void initDataBase() {
        AdminDao adminDao = new AdminDao();

        adminDao.insert(Admin.builder()
                .username("lapuya")
                .email("emaildelaurence@gmail.com")
                .password("mypassword1")
                .build());
        adminDao.insert(Admin.builder()
                .username("amrobinska")
                .email("emaildealex@gmail.com")
                .password("mypassword2")
                .build());
        adminDao.insert(Admin.builder()
                .username("samselem")
                .email("emaildesarah@gmail.com")
                .password("mypassword3").build());

    }
}
