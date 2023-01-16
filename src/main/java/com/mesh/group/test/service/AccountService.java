package com.mesh.group.test.service;

import com.mesh.group.test.request.TransferMoneyRequest;

public interface AccountService {
    void transfer(TransferMoneyRequest transferMoneyRequest);
}
