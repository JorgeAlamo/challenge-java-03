package com.experience.integration.service.payment.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericBaseException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public GenericBaseException(String message) {
        this.message = message;
    }

}
