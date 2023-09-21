package com.aoct.emr.provider.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.aoct.emr.provider.entity.ProviderWorkingScheduleEntity;
import com.aoct.emr.provider.uiRequest.ProviderWorkingScheduleRequest;
import com.aoct.emr.provider.uiResponse.ProviderWorkingScheduleResponse;

public class ProviderWorkingScheduleHelper {

    public static ProviderWorkingScheduleEntity convertFromWorkingScheduleRequest(ProviderWorkingScheduleRequest scheduleRequest) {
        ProviderWorkingScheduleEntity scheduleEntity = new ProviderWorkingScheduleEntity();
        scheduleEntity.setTitle(scheduleRequest.getTitle());
        scheduleEntity.setFacility(scheduleRequest.getFacility());
        scheduleEntity.setWorkingDays(scheduleRequest.getWorkingDays());
        scheduleEntity.setStartTime(scheduleRequest.getStartTime());
        scheduleEntity.setEndTime(scheduleRequest.getEndTime());
        scheduleEntity.setLunchStartTime(scheduleRequest.getLunchStartTime());
        scheduleEntity.setLunchEndTime(scheduleRequest.getLunchEndTime());
        scheduleEntity.setApprovalFlag(scheduleRequest.isApprovalFlag());
        scheduleEntity.setReasonForLeave(scheduleRequest.getReasonForLeave());
        scheduleEntity.setScheduleType(scheduleRequest.getScheduleType());
        scheduleEntity.setStartDate(scheduleRequest.getStartDate());
        scheduleEntity.setEndDate(scheduleRequest.getEndDate());
        scheduleEntity.setLeaveStartTime(scheduleRequest.getLeaveStartTime());
        scheduleEntity.setLeaveEndTime(scheduleRequest.getLeaveEndTime());
        scheduleEntity.setDates(scheduleRequest.getDates());


        return scheduleEntity;
    }
   
    public static ProviderWorkingScheduleResponse convertToWorkingScheduleResponse(ProviderWorkingScheduleEntity scheduleEntity) {
        ProviderWorkingScheduleResponse scheduleResponse = new ProviderWorkingScheduleResponse();
        scheduleResponse.setStartTime(scheduleEntity.getStartTime());
        scheduleResponse.setEndTime(scheduleEntity.getEndTime());
        scheduleResponse.setLunchStartTime(scheduleEntity.getLunchStartTime());
        scheduleResponse.setLunchEndTime(scheduleEntity.getLunchEndTime());
        scheduleResponse.setApprovalFlag(scheduleEntity.isApprovalFlag());
        scheduleResponse.setReasonForLeave(scheduleEntity.getReasonForLeave());
        scheduleResponse.setScheduleType(scheduleEntity.getScheduleType());
        scheduleResponse.setLeaveStartTime(scheduleEntity.getLeaveStartTime());
        scheduleResponse.setLeaveEndTime(scheduleEntity.getLeaveEndTime());
        scheduleResponse.setStartDate(scheduleEntity.getStartDate());
        scheduleResponse.setEndDate(scheduleEntity.getEndDate());
        scheduleResponse.setTitle(scheduleEntity.getTitle());
        scheduleResponse.setScheduleId(scheduleEntity.getScheduleId());

        if(scheduleEntity.getListOfDays()!=null){
            String[] stringArray = scheduleEntity.getListOfDays().split("\\|");

            List<String> stringList = Arrays.asList(stringArray);

            scheduleResponse.setListOfDays(stringList);
        }

        return scheduleResponse;
    }
    public static List<LocalDate> generateDatesInRange(LocalDate startDate, LocalDate endDate, List<DayOfWeek> daysOfWeek) {
        List<LocalDate> datesInRange = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (daysOfWeek.contains(currentDate.getDayOfWeek())) {
                datesInRange.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return datesInRange;
    }

}

