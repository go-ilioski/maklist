package com.example.maklist.repository;

import com.example.maklist.dto.ReportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportDto, Long> {
}
