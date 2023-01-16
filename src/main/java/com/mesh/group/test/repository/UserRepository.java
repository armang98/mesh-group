package com.mesh.group.test.repository;

import com.mesh.group.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u.id, u.name, u.date_of_birth, u.password from users u join email_data ed on u.id = ed.user_id and ed.email = ?", nativeQuery = true)
    Optional<User> findUserIdByEmail(String email);
}
