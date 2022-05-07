package es.ite.felizmente.user.controller;

import es.ite.felizmente.user.model.entity.Admin;
import es.ite.felizmente.user.model.persistence.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminDao adminDao;
    //Used Post to get an Admin by username + password. It is discourage to use get method since the url needs the data
    @PostMapping (path="felizmente/admins/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> getAdmin(@RequestBody Admin admin) {
        System.out.println("Buscando Admin: " + admin.toString());
        Admin a = adminDao.search(admin);
        if(a != null)
            return new ResponseEntity<Admin>(a, HttpStatus.OK);//200 OK
        else
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);//404 NOT FOUND
    }
}
