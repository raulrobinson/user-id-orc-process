package co.com.telefonica.ws.dto;

import co.com.telefonica.ws.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_FORMAT)
    private String timestamp;
    private String message;
    private ServiceResponse serviceResponse;
}
