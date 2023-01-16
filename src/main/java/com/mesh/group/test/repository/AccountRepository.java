package com.mesh.group.test.repository;

import com.mesh.group.test.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUser_Id(Long userId);
}
