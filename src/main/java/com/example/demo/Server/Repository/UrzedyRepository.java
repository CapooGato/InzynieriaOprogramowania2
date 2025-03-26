package com.example.demo.Server.Repository;

import com.example.demo.Server.Models.Urzedy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrzedyRepository extends JpaRepository<Urzedy,Long> {
}
