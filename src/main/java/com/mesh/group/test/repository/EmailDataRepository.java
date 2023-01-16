package com.mesh.group.test.repository;

import com.mesh.group.test.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailDataRepository  extends JpaRepository<EmailData, Long> {

    boolean existsByEmail(String email);

    Optional<EmailData> findByEmailAndUser_Id(String email, Long userId);

    int countAllByUser_Id(Long userId);
}
