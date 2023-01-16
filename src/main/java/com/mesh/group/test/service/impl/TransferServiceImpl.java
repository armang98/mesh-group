package com.mesh.group.test.service.impl;

import com.mesh.group.test.exception.TransferValidationException;
import com.mesh.group.test.model.Account;
import com.mesh.group.test.repository.AccountRepository;
import com.mesh.group.test.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;

    private final Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public void transfer(Long userFromId, Long userToId, BigDecimal value) {
        lock.lock();
        try {
            // Validation: check if both users exist in the database
            Account transferFrom = accountRepository.findAccountByUser_Id(userFromId).orElseThrow(() -> new TransferValidationException("Invalid transfer from user id"));
            Account transferTo = accountRepository.findAccountByUser_Id(userToId).orElseThrow(() -> new TransferValidationException("Invalid transfer to user id"));

            // Validation: check if the transferFrom user has enough balance to make the transfer
            if (transferFrom.getBalance().compareTo(value) < 0) {
                throw new TransferValidationException("Insufficient balance for transfer");
            }

            // Validation: check if the transferFrom user has enough balance to make the transfer
            if (transferFrom.getBalance().subtract(value).compareTo(value) < 0) {
                throw new TransferValidationException("Balance can't be less than 0");
            }

            // Validation: check if the transfer value is greater than zero
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                throw new TransferValidationException("Invalid transfer value, it must be greater than zero");
            }

            // Perform the transfer
            transferFrom.setBalance(transferFrom.getBalance().subtract(value));
            transferTo.setBalance(transferTo.getBalance().add(value));

            // Save the updated users to the database
            accountRepository.save(transferFrom);
            accountRepository.save(transferTo);
        } finally {
            lock.unlock();
        }
    }
}