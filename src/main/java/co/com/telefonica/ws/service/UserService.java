package co.com.telefonica.ws.service;

import java.util.Date;

public interface UserService {
    String getRegistersPaginadosPorLoadDateOdsUser(Date loadDate, int pageSize, int pageNumber);
}
