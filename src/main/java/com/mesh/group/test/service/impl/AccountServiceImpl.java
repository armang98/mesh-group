package com.mesh.group.test.service.impl;

import com.mesh.group.test.model.User;
import com.mesh.group.test.request.TransferMoneyRequest;
import com.mesh.group.test.service.AccountService;
import com.mesh.group.test.service.TransferService;
import com.mesh.group.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final TransferService transferService;
    private final UserService userService;

    @Override
    public void transfer(TransferMoneyRequest transferMoneyRequest) {
        User user = userService.loadLoggedInUser();
        transferService.transfer(user.getId(), transferMoneyRequest.getUserToId(), transferMoneyRequest.getValue());
    }
}