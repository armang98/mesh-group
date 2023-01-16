package com.mesh.group.test.service;

import com.mesh.group.test.exception.TransferValidationException;
import com.mesh.group.test.exception.UnauthorizedException;
import com.mesh.group.test.model.Account;
import com.mesh.group.test.model.User;
import com.mesh.group.test.repository.AccountRepository;
import com.mesh.group.test.repository.UserRepository;
import com.mesh.group.test.request.TransferMoneyRequest;
import com.mesh.group.test.service.impl.AccountServiceScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MockBean(AccountServiceScheduler.class)
public class TransferMoneyServiceTest {
    private static final String USER_TEST = "test@test.ru";
    private static final String USER_TEST_2 = "test2@test.ru";
    private static final String USER_PASS = "test";

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:11.1");
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", database::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Test
    @WithMockUser(username = USER_TEST_2, password = USER_PASS)
    public void transferService_testSuccess() {
        TransferMoneyRequest transferMoneyRequest = buildTransferMoneyRequest();
        accountService.transfer(transferMoneyRequest);
        Optional<Account> afterTransfer = accountRepository.findAccountByUser_Id(transferMoneyRequest.getUserToId());

        Assertions.assertTrue(afterTransfer.get().getBalance().doubleValue() > afterTransfer.get().getInitialDeposit().doubleValue());
        Assertions.assertEquals(0, afterTransfer.get().getBalance().subtract(afterTransfer.get().getInitialDeposit()).compareTo(BigDecimal.TEN));
    }

    @Test
    @WithMockUser(username = USER_TEST, password = USER_PASS)
    public void transferService_testFailed_incorrectToUserId_notFound() {
        TransferMoneyRequest transferMoneyRequest = buildTransferMoneyRequest();
        transferMoneyRequest.setUserToId(-1L);
        Assertions.assertThrows(TransferValidationException.class, () -> accountService.transfer(transferMoneyRequest), "Invalid transfer to user id");
    }

    @Test
    @WithMockUser(username = USER_TEST, password = USER_PASS)
    public void transferService_testFailed_invalidFromUserBalance_negative() {
        TransferMoneyRequest transferMoneyRequest = buildTransferMoneyRequest();
        User user = userRepository.findUserIdByEmail(USER_TEST)
                .orElseThrow(UnauthorizedException::new);
        Optional<Account> account = accountRepository.findAccountByUser_Id(user.getId());
        account.get().setBalance(BigDecimal.valueOf(-50L));
        accountRepository.save(account.get());
        Assertions.assertThrows(TransferValidationException.class, () -> accountService.transfer(transferMoneyRequest), "Insufficient balance for transfer");
    }

    @Test
    @WithMockUser(username = USER_TEST, password = USER_PASS)
    public void transferService_testFailed_invalidFromUserBalanceSubtract() {
        TransferMoneyRequest transferMoneyRequest = buildTransferMoneyRequest();
        transferMoneyRequest.setValue(BigDecimal.valueOf(Short.MAX_VALUE));
        Assertions.assertThrows(TransferValidationException.class, () -> accountService.transfer(transferMoneyRequest), "Balance can't be less than 0");
    }

    @Test
    @WithMockUser(username = USER_TEST, password = USER_PASS)
    public void transferService_testFailed_invalidTransferValue_negative() {
        TransferMoneyRequest transferMoneyRequest = buildTransferMoneyRequest();
        transferMoneyRequest.setValue(BigDecimal.valueOf(Short.MIN_VALUE));
        Assertions.assertThrows(TransferValidationException.class, () -> accountService.transfer(transferMoneyRequest), "Invalid transfer value, it must be greater than zero");
    }

    private TransferMoneyRequest buildTransferMoneyRequest() {
        return TransferMoneyRequest.builder()
                .userToId(1L)
                .value(BigDecimal.TEN)
                .build();
    }
}