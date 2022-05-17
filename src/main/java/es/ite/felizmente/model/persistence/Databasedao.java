package es.ite.felizmente.model.persistence;

import es.ite.felizmente.model.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Base64;

/*
     1. We are aware that setting passwords this way is a huge breach of security. We are doing it in this manner
     for debugging purposes. Implement encryption.
     2. It is called "Databasedao" and not DataBaseDao because we had problems with naming convention in main class.
 */

@Data
@AllArgsConstructor
@Service
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


    public void initDataBase(Cipher encryptor) {
        AdminDao adminDao = new AdminDao();

        String laurencePassword = "mypassword1";
        String alexPassword = "mypassword2";
        String saraPassword = "mypassword3";
        try {
            adminDao.insert(Admin.builder()
                    .username("lapuya")
                    .email("emaildelaurence@gmail.com")
                    .password(Base64.getEncoder().encodeToString(encryptor.doFinal(laurencePassword.getBytes())))
                    .build());
            adminDao.insert(Admin.builder()
                    .username("amrobinska")
                    .email("emaildealex@gmail.com")
                    .password(Base64.getEncoder().encodeToString(encryptor.doFinal(alexPassword.getBytes())))
                    .build());
            adminDao.insert(Admin.builder()
                    .username("samselem")
                    .email("emaildesarah@gmail.com")
                    .password(Base64.getEncoder().encodeToString(encryptor.doFinal(saraPassword.getBytes())))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}