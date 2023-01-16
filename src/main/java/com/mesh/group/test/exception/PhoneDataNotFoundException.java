package com.mesh.group.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class PhoneDataNotFoundException extends RuntimeException {
    public PhoneDataNotFoundException(String phone) {
        log.debug("PhoneData with given phone not found: [{}]", phone);
    }

    public PhoneDataNotFoundException(String phone, Long userId) {
        log.debug("PhoneData with given phone and userId not found: [phone: {}, userId: {}]", phone, userId);
    }
}
