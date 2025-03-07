package com.example.demo.Server.Repository;

import com.example.demo.Server.Models.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUsersRepository extends JpaRepository<AppUsers, Long> {
}
