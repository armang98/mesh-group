package com.mesh.group.test.service.impl;

import com.mesh.group.test.model.Account;
import com.mesh.group.test.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountServiceScheduler {
    private static final double MAX_BALANCE_INCREASE_PERCENTAGE = 207;
    private static final double BALANCE_INCREASE_PERCENTAGE = 10;
    private static final long BALANCE_UPDATE_INTERVAL_SECONDS = 30;
    private static final int NUM_THREADS = 5;
    
    private final AccountRepository accountRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    @Scheduled(fixedRate = BALANCE_UPDATE_INTERVAL_SECONDS * 1000)
    public void reportCurrentTime() {
        List<Account> clients = accountRepository.findAll();
        clients.forEach(account -> {
            executorService.submit(() -> {
                double newBalance = account.getBalance().doubleValue() * (1 + BALANCE_INCREASE_PERCENTAGE / 100);
                double maxBalance = account.getInitialDeposit().doubleValue() * (1 + MAX_BALANCE_INCREASE_PERCENTAGE / 100);
                if (newBalance < maxBalance) {
                    double previousBalance = account.getBalance().doubleValue();
                    account.setBalance(BigDecimal.valueOf(newBalance));
                    accountRepository.save(account);
                    log.info("Account {} balance changed from {} to {}", account.getId(), previousBalance, newBalance);
                }
            });
        });
    }
}