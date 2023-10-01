package com.example.maklist.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity(name = "report_details")
public class ReportDto {

    @Id
    private Long reportId;

    private Instant reportDate;

    private String reportDescription;

    private String reportedUserEmail;

    private String creatorUserEmail;


}
