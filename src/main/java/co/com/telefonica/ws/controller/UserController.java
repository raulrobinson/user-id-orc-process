package co.com.telefonica.ws.controller;

import co.com.telefonica.ws.dto.ResponseDto;
import co.com.telefonica.ws.dto.ServiceResponse;
import co.com.telefonica.ws.service.UserService;
import co.com.telefonica.ws.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/oracle")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/sent-to-pg/{loadDate}/{pageSize}/{pageNumber}")
    public ResponseEntity<ResponseDto> sentToPg(@PathVariable String loadDate,
                           @PathVariable int pageSize,
                           @PathVariable int pageNumber) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(loadDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        var require = service.getRegistersPaginadosPorLoadDateOdsUser(parsedDate, pageSize, pageNumber);
        if (require.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .timestamp(Utilities.blindStr(Utilities.getTimestampValue()))
                    .message(Utilities.blindStr(String.valueOf(require.getStatusCode())))
                    .serviceResponse(ServiceResponse.builder()
                            .item(Utilities.blindStr("Sent Status"))
                            .status(Utilities.blindStr(require.getBody()))
                            .build())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseDto.builder()
                .timestamp(Utilities.blindStr(Utilities.getTimestampValue()))
                .message(Utilities.blindStr(String.valueOf(require.getStatusCode())))
                .serviceResponse(ServiceResponse.builder()
                        .item(Utilities.blindStr("Sent Status"))
                        .status(Utilities.blindStr(require.getBody()))
                        .build())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
