package es.ite.felizmente.controller;

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

import es.ite.felizmente.model.persistence.UserDao;
import es.ite.felizmente.model.entity.User;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;

	//GET -> get a User by email
	@GetMapping(path="felizmente/users/registration/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("email") String email) {
        System.out.println("Buscando User con email: " + email);
        User u = userDao.search(email);
        if(u != null)
            return new ResponseEntity<User>(u,HttpStatus.OK);//200 OK
        else
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);//404 NOT FOUND

    }

    //GET -> get an User id
    @GetMapping(path="felizmente/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User u = userDao.searchById(id);
        if(u != null)
            return new ResponseEntity<User>(u, HttpStatus.OK);//200 OK
        else
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);//404 NOT FOUND
    }

	//Post -> to register a user
	@PostMapping(path="felizmente/users/",consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User u) {
        System.out.println("registerUser: " + u);
        if (userDao.insert(u) != null)
        	return new ResponseEntity<User>(u,HttpStatus.CREATED);//201 CREATED
        else
        	return new ResponseEntity<User>(u,HttpStatus.BAD_REQUEST); //Bad request, we cant have users with same id and name
    }

	//list all users
	@GetMapping(path="felizmente/users/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listUsers() {
        List<User> listaUsers = null;

        listaUsers = userDao.list();
        System.out.println(listaUsers);
        return new ResponseEntity<List<User>>(listaUsers,HttpStatus.OK);
    }

	//Put -> modify by Id
	@PutMapping(path="felizmente/users/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modifyUser(
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

	//DELETE -> delete a user by id
	@DeleteMapping(path="felizmente/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        System.out.println("Id of the user to delete: " + id);
        if(userDao.delete(id)) {
            return new ResponseEntity<String>("User deleted",HttpStatus.OK);//200 OK
        }else {
            return new ResponseEntity<String>("User does not exist",HttpStatus.NOT_FOUND);//404 NOT FOUND
        }
    }

    //Used Post to get an User by username + password. It is discourage to use get method since the url needs the data
    @PostMapping (path="felizmente/users/login/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserForLogin(@RequestBody User user) {
        System.out.println("Buscando User: " + user.toString());
        User u = userDao.searchByUser(user);
        if(u != null)
            return new ResponseEntity<User>(u, HttpStatus.OK);//200 OK
        else
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);//404 NOT FOUND
    }
}

