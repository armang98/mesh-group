package com.mesh.group.test.service;

import java.math.BigDecimal;

public interface TransferService {
    void transfer(Long userFromId, Long userToId, BigDecimal value);
}
