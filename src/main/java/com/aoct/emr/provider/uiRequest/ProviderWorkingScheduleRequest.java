package com.aoct.emr.provider.uiRequest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ProviderWorkingScheduleRequest {
    private String title;
    private String facility;
    private List<LocalDate> workingDays;
    private String startTime;
    private String endTime;
    private String lunchStartTime;
    private String lunchEndTime;
    private boolean approvalFlag;
    private String reasonForLeave;
    private String scheduleType;
    private Long providerId; // The ID of the provider associated with this schedule

    private String leaveStartTime;
    private String leaveEndTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DayOfWeek> days;
    private String dates;
}
