package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM app_user where email in :email")
    List<AppUser> findAllByEmail(List<String> email);
}
