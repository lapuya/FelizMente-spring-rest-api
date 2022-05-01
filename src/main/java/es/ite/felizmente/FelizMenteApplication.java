package es.ite.felizmente;

import es.ite.felizmente.user.model.persistence.Databasedao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FelizMenteApplication {

	public static ApplicationContext context;
	public static void main(String[] args)  {
		context = SpringApplication.run(FelizMenteApplication.class, args);
		Databasedao dataBaseDao = context.getBean("databasedao", Databasedao.class);
		if (!dataBaseDao.alreadyWithAdmins()) {
			dataBaseDao.initDataBase();
		}
	}
}
