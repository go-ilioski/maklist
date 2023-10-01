package com.example.maklist.repository;

import com.example.maklist.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDto,Long> {
    //Optional<UserDto> findByEmail(String email);
    UserDto findByEmail(String email);
}
