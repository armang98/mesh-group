package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Slf4j
public class EmailDataExistsException extends RuntimeException {
    public EmailDataExistsException(String email) {
        log.debug("EmailData with given email already exists: [{}]", email);
    }
}
