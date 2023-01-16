package com.mesh.group.test.repository;

import com.mesh.group.test.model.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    boolean existsByPhone(String phone);

    Optional<PhoneData> findByPhoneAndUser_Id(String email, Long userId);

    int countAllByUser_Id(Long userId);
}
