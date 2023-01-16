package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class EmailDataLessCountException extends RuntimeException {
    public EmailDataLessCountException(Long id) {
        log.debug("EmailData for user with id: [{}] couldn't be less than 1", id);
    }
}
