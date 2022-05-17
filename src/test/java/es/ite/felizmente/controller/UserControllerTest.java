package es.ite.felizmente.controller;

import es.ite.felizmente.model.entity.User;
import es.ite.felizmente.model.persistence.UserDao;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_a_valid_email_returns_ok() {
        when(userDao.search(any())).thenReturn(User.builder().build());

        var response = userController.getUser(anyString());
        assertNotNull(response);
        verify(userDao, times(1)).search(any());
    }

    @Test
    public void given_an_invalid_email_returns_not_found() {
        when(userDao.search(any())).thenReturn(null);

        var response = userController.getUser(anyString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userDao, times(1)).search(any());
    }

    @Test
    public void given_an_id_to_search_returns_ok() {
        when(userDao.searchById(anyInt())).thenReturn(User.builder().build());

        var response = userController.getUserById(anyInt());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userDao, times(1)).searchById(anyInt());
    }

    @Test
    public void given_an_id_to_search_returns_not_found() {
        when(userDao.searchById(anyInt())).thenReturn(null);

        var response = userController.getUserById(anyInt());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userDao, times(1)).searchById(anyInt());
    }

    @Test
    public void given_a_new_user_returns_ok() {
        when(userDao.insert(any())).thenReturn(User.builder().build());

        var response = userController.registerUser(any());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userDao, times(1)).insert(any());
    }

    @Test
    public void given_an_existing_user_returns_bad_request() {
        when(userDao.insert(any())).thenReturn(null);

        var response = userController.registerUser(any());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userDao, times(1)).insert(any());
    }

    @Test
    public void given_list_request_returns_list() {
        List<User> userLists = new ArrayList<>();
        when(userDao.list()).thenReturn(userLists);

        var response = userController.listUsers();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userDao, times(1)).list();
    }

    @Test
    public void given_a_valid_id_to_modify_user_returns_ok() {
        when(userDao.update(any())).thenReturn(User.builder().build());

        var response = userController.modifyUser(anyInt(), User.builder().build());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userDao, times(1)).update(any());
    }

    @Test
    public void given_an_invalid_id_to_modify_user_returns_not_found() {
        when(userDao.update(any())).thenReturn(null);

        var response = userController.modifyUser(anyInt(), User.builder().build());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userDao, times(1)).update(any());
    }

    @Test
    public void given_a_valid_id_to_delete_returns_ok() {
        when(userDao.delete(anyInt())).thenReturn(true);

        var response = userController.deleteUser(anyInt());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userDao, times(1)).delete(anyInt());
    }

    @Test
    public void given_invalid_id_to_delete_returns_not_found() {
        when(userDao.delete(anyInt())).thenReturn(false);

        var response = userController.deleteUser(anyInt());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userDao, times(1)).delete(anyInt());
    }

    @Test
    public void given_an_existing_user_returns_ok() {
        when(userDao.searchByUser(any())).thenReturn(User.builder().build());

        var response = userController.getUserForLogin(User.builder().build());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userDao, times(1)).searchByUser(any());
    }

    @Test
    public void given_an_invalid_user_returns_not_found() {
        when(userDao.searchByUser(any())).thenReturn(null);

        var response = userController.getUserForLogin(User.builder().build());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userDao, times(1)).searchByUser(any());
    }




}
