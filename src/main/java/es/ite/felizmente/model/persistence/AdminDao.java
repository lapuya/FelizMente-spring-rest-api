package es.ite.felizmente.model.persistence;

import es.ite.felizmente.model.entity.Admin;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.persistence.*;
import java.util.Base64;

@Data
@Component
public class AdminDao {

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

        Admin aAux = em.createQuery("select admin from Admin admin where admin.username = :username", Admin.class)
                .setParameter("username", username)
                .getSingleResult();
        try {
             if (aAux != null) {
                encryptor.init(Cipher.DECRYPT_MODE, scytale);
                byte [] passwordBytes = encryptor.doFinal(Base64.getDecoder().decode(aAux.getPassword()));
                String passwordString = new String (passwordBytes);
                if (a.getPassword().equals(passwordString)) {
                    return aAux;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }



}
