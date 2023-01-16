package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
      log.debug("User with given id not found: [{}]", userId);
    }
}
