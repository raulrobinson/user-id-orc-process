package co.com.telefonica.ws.service;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface UserService {
    ResponseEntity<String> getRegistersPaginadosPorLoadDateOdsUser(Date loadDate, int pageSize, int pageNumber);
}
