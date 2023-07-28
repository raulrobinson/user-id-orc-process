package co.com.telefonica.ws.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceResponse {
    private String item;
    private String status;
}
