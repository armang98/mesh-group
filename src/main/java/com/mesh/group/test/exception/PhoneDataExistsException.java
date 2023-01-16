package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Slf4j
public class PhoneDataExistsException extends RuntimeException {
    public PhoneDataExistsException(String phone) {
        log.debug("PhoneData with given phone already exists: [{}]", phone);
    }
}
