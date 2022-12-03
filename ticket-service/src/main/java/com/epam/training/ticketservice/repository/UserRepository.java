package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsernameAndPasswordAndRole(String username, String password, UserEntity.Role role);

}
