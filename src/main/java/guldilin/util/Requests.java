package guldilin.util;

import guldilin.dto.ErrorDTO;
import guldilin.dto.ValidationErrorDTO;

import javax.ws.rs.core.Response;

public class Requests {
    public static void handleResponseError(Response response) {
        try {
            ErrorDTO errorDTO = response.readEntity(ErrorDTO.class);
            System.out.println(errorDTO.getError());
            System.out.println(errorDTO.getMessage());
        } catch (Exception e) {
            try {
                ValidationErrorDTO errorDTO = response.readEntity(ValidationErrorDTO.class);
                System.out.println(errorDTO.getError());
                errorDTO.getMessage()
                        .forEach((key, value) -> System.out.println("Error: " + key + " - " + value));
            } catch (Exception x) {
                System.out.println("Error happened");
            }
        }
    }
}
