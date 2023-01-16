package com.mesh.group.test.resource;

import com.mesh.group.test.request.TransferMoneyRequest;
import com.mesh.group.test.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
@Api(value = "Accounts API")
public class AccountResource {
    private final AccountService accountService;

    @PostMapping("/transfer")
    @ApiOperation(value = "Post request to transfer money between accounts")
    public ResponseEntity<?> transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) {
        accountService.transfer(transferMoneyRequest);
        return ResponseEntity.ok().build();
    }

}
