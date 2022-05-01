package es.ite.felizmente.user.controller;

import es.ite.felizmente.user.model.entity.Admin;
import es.ite.felizmente.user.model.entity.User;
import es.ite.felizmente.user.model.persistence.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminDao adminDao;
    //GET -> get an Admin by username + password
    @GetMapping(path="felizmente/admins/{token}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> getAdmin(@PathVariable("token") String token) {
        System.out.println("Buscando Admin con token: " + token);
        Admin a = adminDao.search(token);
        if(a != null)
            return new ResponseEntity<Admin>(a, HttpStatus.OK);//200 OK
        else
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);//404 NOT FOUND

    }
}
