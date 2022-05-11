package es.ite.felizmente;

import es.ite.felizmente.model.persistence.AdminDao;
import es.ite.felizmente.model.persistence.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import es.ite.felizmente.model.persistence.Databasedao;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


@SpringBootApplication
public class FelizMenteApplication {


	public static ApplicationContext context;
	public static void main(String[] args)  {

		context = SpringApplication.run(FelizMenteApplication.class, args);
		Databasedao dataBaseDao = context.getBean("databasedao", Databasedao.class);
		AdminDao adminDao = context.getBean("adminDao", AdminDao.class);
		UserDao userDao = context.getBean("userDao", UserDao.class);

		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecretKey scytale = generator.generateKey();
			Cipher encryptor = Cipher.getInstance("AES");
			encryptor.init(Cipher.ENCRYPT_MODE, scytale);
			adminDao.setEncryptor(encryptor);
			adminDao.setScytale(scytale);
			userDao.setEncryptor(encryptor);
			userDao.setScytale(scytale);
			if (!dataBaseDao.alreadyWithAdmins()) {
				dataBaseDao.initDataBase(encryptor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}