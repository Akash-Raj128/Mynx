package com.aoct.emr.provider.bl;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aoct.emr.common.exception.DuplicateProviderException;
import com.aoct.emr.common.exception.InvalidNpiException;
import com.aoct.emr.provider.entity.ProviderEntity;
import com.aoct.emr.provider.entity.ProviderWorkingScheduleEntity;
import com.aoct.emr.provider.entity.ReferringProvider;
import com.aoct.emr.provider.externalResponseModel.ExternalServiceResponseModel;
import com.aoct.emr.provider.repository.ProviderRepo;
import com.aoct.emr.provider.service.ExternalService;
import com.aoct.emr.provider.service.ProviderService;
import com.aoct.emr.provider.uiRequest.GetProviderWorkingScheduleUIReq;
import com.aoct.emr.provider.uiRequest.ProviderUIRequest;
import com.aoct.emr.provider.uiRequest.ProviderWorkingScheduleRequest;
import com.aoct.emr.provider.uiResponse.ProviderUiResponse;
import com.aoct.emr.provider.uiResponse.ProviderWorkingScheduleResponse;
import com.aoct.emr.provider.utility.ProviderHelper;
import com.aoct.emr.provider.utility.ProviderWorkingScheduleHelper;

@Component
public class ProviderBl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ProviderService service;

	@Autowired
	ExternalService externalService;

	@Autowired
	ProviderRepo providerRepo;

	public ReferringProvider getReferringProviderDetails(String npiNumber) {
		return service.getReferringProviderDetails(npiNumber);

	}

	public Long createProvider(ProviderUIRequest providerUIRequest) {

		ExternalServiceResponseModel externalNpiCallResponse = externalService
				.callExternalNpiAPi(providerUIRequest.getNpi());

		if (externalNpiCallResponse.getResults() == null || externalNpiCallResponse.getResults().size() < 1)

		{
			throw new InvalidNpiException("NPI number does not exist.Please retry with correct NPI");
		}
		String npi = providerUIRequest.getNpi();
		String firstName = providerUIRequest.getFirstName();
		String lastName = providerUIRequest.getLastName();
		List<ProviderEntity> existingProviders = service.findProvidersByNpiAndName(npi, firstName, lastName);
		if (!existingProviders.isEmpty()) {
			throw new DuplicateProviderException("Provider with the same NPI and name already exists.");
		}
		ProviderUIRequest providerRequest = ProviderHelper.checkConflictAddProvider(providerUIRequest,
				externalNpiCallResponse);
		ProviderEntity p = ProviderHelper.convertFromProviderRequest(providerRequest);
		// ProviderEntity p =
		// ProviderHelper.convertFromProviderRequest(providerUIRequest); //to be deleted
		Long providerId = service.createProvider(p);

		return providerId;
	}

	public String searchProvider(String npiNumber) {
		return null;
	}

	public ProviderUiResponse getProviderDetail(Long providerId) {
		ProviderEntity provider = service.getProviderDetail(providerId);
		ProviderUiResponse response = ProviderHelper.convertToProviderUiResponse(provider);
		return response;

	}

	public List<ProviderUiResponse> getAllProviderDetails() {
		System.out.println("Method Invoked");
		List<ProviderEntity> providers = service.getAllProviderDetails();

		List<ProviderUiResponse> response = ProviderHelper.ConvertToListOfProviderUiResponse(providers);
		return response;

	}

	public List<String> getAllSpeciality() {

		return service.getAllSpeciality();
	}

	public List<ProviderUiResponse> getProvidersBySpeciality(String speciality) {
		List<ProviderEntity> providers = service.getProvidersBySpeciality(speciality);
		List<ProviderUiResponse> response = ProviderHelper.ConvertToListOfProviderUiResponse(providers);
		return response;
	}

	public Long addProviderWorkingSchedule(ProviderWorkingScheduleRequest scheduleRequest) {

		ProviderEntity provider = service.getProviderById(scheduleRequest.getProviderId());
		LocalDate startDate = scheduleRequest.getStartDate();
		LocalDate endDate = scheduleRequest.getEndDate();
		List<DayOfWeek> days = scheduleRequest.getDays();
		String listOfDays = "";
		for (DayOfWeek dayOfWeek : days) {
			listOfDays += dayOfWeek + "|";
		}

		if (days != null) {
			List<LocalDate> datesInRange = ProviderWorkingScheduleHelper.generateDatesInRange(startDate, endDate, days);
			String dates = "";
			for (LocalDate date : datesInRange) {
				dates += date + "|";
			}
			scheduleRequest.setDates(dates);
			scheduleRequest.setWorkingDays(datesInRange);
		}

		if (provider != null) {
			ProviderWorkingScheduleEntity scheduleEntity = ProviderWorkingScheduleHelper
					.convertFromWorkingScheduleRequest(scheduleRequest);
			scheduleEntity.setListOfDays(listOfDays);
			scheduleEntity.setProvider(provider);
			provider.getWorkingSchedules().add(scheduleEntity);

			providerRepo.save(provider);

			return scheduleEntity.getScheduleId();
		}

		return null;
	}

	public List<ProviderWorkingScheduleResponse> getProviderWorkingSchedule(GetProviderWorkingScheduleUIReq request) {
		// ProviderEntity provider = providerRepo.getById(request.getProviderId());
		ProviderEntity provider = service.getProviderById(request.getProviderId());
		if (provider != null) {
			List<ProviderWorkingScheduleResponse> scheduleResponses = new ArrayList<>();
			for (ProviderWorkingScheduleEntity scheduleEntity : provider.getWorkingSchedules()) {
				for (LocalDate workingDay : scheduleEntity.getWorkingDays()) {
					if (workingDay.getYear() == request.getYear() && workingDay.getMonthValue() == request.getMonth()) {
						ProviderWorkingScheduleResponse scheduleResponse = ProviderWorkingScheduleHelper
								.convertToWorkingScheduleResponse(scheduleEntity);
						scheduleResponse.setWorkingDay(workingDay);
						scheduleResponses.add(scheduleResponse);
					}
				}
			}
			return scheduleResponses;
		}
		return Collections.emptyList();
	}

	public List<ProviderWorkingScheduleResponse> getProviderSchedule(Long providerId) {
		List<ProviderWorkingScheduleEntity> schedules = service.getProviderSchedule(providerId);
		List<ProviderWorkingScheduleResponse> scheduleResponses = new ArrayList<>();
		for (ProviderWorkingScheduleEntity schedule : schedules) {

			ProviderWorkingScheduleResponse scheduleResponse = ProviderWorkingScheduleHelper
					.convertToWorkingScheduleResponse(schedule);

			scheduleResponses.add(scheduleResponse);
		}
		return scheduleResponses;
	}

	public List<ProviderWorkingScheduleResponse> getProviderScheduleV2(GetProviderWorkingScheduleUIReq request) {
		ProviderEntity provider = service.getProviderById(request.getProviderId());
		if (provider != null) {
			List<ProviderWorkingScheduleResponse> scheduleResponses = new ArrayList<>();
			for (ProviderWorkingScheduleEntity scheduleEntity : provider.getWorkingSchedules()) {
				List<LocalDate> dateList = new ArrayList<LocalDate>();
				if(scheduleEntity.getDates()!=null) {
					String[] datesInString =scheduleEntity.getDates().split("\\|");
					for (String dateString : datesInString) {
						try {
							LocalDate date = LocalDate.parse(dateString);
							dateList.add(date);
						} catch (Exception e) {
							System.err.println("Error parsing date: " + dateString);
						}
					}
				}
				 
				
				if (dateList != null) {
					for (LocalDate workingDay : dateList) {
						if (workingDay.getYear() == request.getYear()
								&& workingDay.getMonthValue() == request.getMonth()) {
							ProviderWorkingScheduleResponse scheduleResponse = ProviderWorkingScheduleHelper
									.convertToWorkingScheduleResponse(scheduleEntity);
							scheduleResponse.setWorkingDay(workingDay);
							scheduleResponses.add(scheduleResponse);
						}

					}
				}

			}
			return scheduleResponses;
		}
		return null;
	}

}
