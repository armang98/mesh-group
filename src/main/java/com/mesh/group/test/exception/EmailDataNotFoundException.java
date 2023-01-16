package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class EmailDataNotFoundException extends RuntimeException {
    public EmailDataNotFoundException(String email) {
        log.debug("EmailData with given email not found: [{}]", email);
    }

    public EmailDataNotFoundException(String email, Long userId) {
        log.debug("EmailData with given email and userId not found: [email: {}, userId: {}]", email, userId);
    }
}
