package co.com.telefonica.ws.service;

import co.com.telefonica.ws.domain.OdsUser;
import co.com.telefonica.ws.dto.PgUserDTO;
import co.com.telefonica.ws.repository.OdsUserRepository;
import co.com.telefonica.ws.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${ms.postgres.url}")
    private String msUrlPg;

    private final RestTemplate restTemplate;
    private final OdsUserRepository odsUserRepository;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, OdsUserRepository odsUserRepository) {
        this.restTemplate = restTemplate;
        this.odsUserRepository = odsUserRepository;
    }
    @Override
    public String getRegistersPaginadosPorLoadDateOdsUser(Date loadDate, int pageSize, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<OdsUser> page = odsUserRepository.findByLoadDate(loadDate, pageRequest);
        var userList =  convertBatchOdsToPgPage(page);
        return sendToPgThree(userList);
    }
    public String sendToPgThree(List<PgUserDTO> userList) {
        try {
            String url = msUrlPg;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<PgUserDTO>> requestEntity = new HttpEntity<>(userList, headers);
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info(Constants.RESPONSE_TOTAL, userList.size());
                return "200";
            }
        } catch (ResourceAccessException ex) {
            log.error(Constants.RESPONSE_TOTAL, userList.size());
            return "400";
        }
        log.error(Constants.RESPONSE_TOTAL, userList.size());
        return "500";
    }
    public List<PgUserDTO> convertBatchOdsToPgPage(Page<OdsUser> usersPage) {
        List<PgUserDTO> userList = new ArrayList<>();
        for (OdsUser pgItem : usersPage) {
            PgUserDTO pgUser = new PgUserDTO();
            pgUser.setIdType(pgItem.getIdType());
            pgUser.setIdNumber(pgItem.getIdNumber());
            userList.add(pgUser);
        }
        return userList;
    }
}
