package es.ite.felizmente.controller;

import es.ite.felizmente.model.entity.Admin;
import es.ite.felizmente.model.persistence.AdminDao;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    AdminDao adminDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_an_admin_returns_ok() {
        when(adminDao.search(any())).thenReturn(Admin.builder().build());

        var response = adminController.getAdmin(Admin.builder().build());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminDao, times(1)).search(any());
    }

    @Test
    public void given_a_null_admin_returns_not_found() {
        when(adminDao.search(any())).thenReturn(null);

        var response = adminController.getAdmin(Admin.builder().build());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(adminDao, times(1)).search(any());
    }
}
