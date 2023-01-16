package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class TransferValidationException extends IllegalArgumentException {
    public TransferValidationException(String message) {
        super(message);
        log.debug("Transfer validation failed: {}", message);
    }
}
