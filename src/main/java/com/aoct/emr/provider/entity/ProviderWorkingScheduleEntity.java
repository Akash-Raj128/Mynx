package com.aoct.emr.provider.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "provider_working_schedule")
public class ProviderWorkingScheduleEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleId")
    private Long scheduleId;

    private String title;
    private String facility;

    @ElementCollection
    @CollectionTable(name = "working_days", joinColumns = @JoinColumn(name = "scheduleId"))
    @Column(name = "working_day")
    private List<LocalDate> workingDays;

    private String startTime;
    private String endTime;
    private String lunchStartTime;
    private String lunchEndTime;
    private boolean approvalFlag;
    private String reasonForLeave;
    private String scheduleType;
    private String leaveStartTime;
    private String leaveEndTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String listOfDays;

    @ManyToOne
    @JoinColumn(name = "providerId")
    private ProviderEntity provider;
    
    private String dates;
}
