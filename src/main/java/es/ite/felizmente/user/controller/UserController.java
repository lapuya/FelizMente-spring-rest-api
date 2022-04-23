package es.ite.felizmente.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.ite.felizmente.user.model.persistence.UserDao;
import es.ite.felizmente.user.model.entity.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDao userDao;
	

	//GET -> get a User by id
	@GetMapping(path="felizmente/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        System.out.println("Buscando User con id: " + id);
        User u = userDao.search(id);
        if(u != null) 
            return new ResponseEntity<User>(u,HttpStatus.OK);//200 OK
        else 
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);//404 NOT FOUND
        
    }
	
	//Post -> to register a user
	@PostMapping(path="felizmente/users",consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> altaUser(@RequestBody User u) {
        System.out.println("altaUser: objeto User: " + u);
        if (userDao.insert(u) != null)
        	return new ResponseEntity<User>(u,HttpStatus.CREATED);//201 CREATED
        else
        	return new ResponseEntity<User>(u,HttpStatus.BAD_REQUEST); //Bad request, we cant have users with same id and name
    }
	
	//list all users
	@GetMapping(path="felizmente/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listarUser() {
        List<User> listaUsers = null;
        
        listaUsers = userDao.list();
        System.out.println(listaUsers);
        return new ResponseEntity<List<User>>(listaUsers,HttpStatus.OK);
    }
	
	//Put -> modify by Id
	@PutMapping(path="felizmente/users/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modificarUser(
            @PathVariable("id") int id, 
            @RequestBody User u) {
        System.out.println("ID to modify: " + id);
        System.out.println("Data to modify: " + u);
        u.setId(id);
        User userUpdate = userDao.update(u);
        if(userUpdate != null) {
            return new ResponseEntity<User>(HttpStatus.OK);//200 OK
        }else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);//404 NOT FOUND
        }
    }
	
	//DELETE -> delete a user by Id
	@DeleteMapping(path="felizmente/users/{id}")
    public ResponseEntity<User> borrarUser(@PathVariable("id") int id) {
        System.out.println("ID a borrar: " + id);
        User u = userDao.delete(id);
        if(u != null) {
            return new ResponseEntity<User>(u,HttpStatus.OK);//200 OK
        }else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);//404 NOT FOUND
        }
    }
}
