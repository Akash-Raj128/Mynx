package com.aoct.emr.provider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aoct.emr.provider.bl.ProviderBl;
import com.aoct.emr.provider.uiRequest.GetProviderWorkingScheduleUIReq;
import com.aoct.emr.provider.uiRequest.ProviderUIRequest;
import com.aoct.emr.provider.uiRequest.ProviderWorkingScheduleRequest;
import com.aoct.emr.provider.uiResponse.ProviderUiResponse;
import com.aoct.emr.provider.uiResponse.ProviderWorkingScheduleResponse;

@RestController
@RequestMapping("/provider")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ProviderController {
   @Autowired
   ProviderBl bl;
   
   @PostMapping("/createProvider")
   @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
   public Long addProviderDetails(@RequestBody ProviderUIRequest providerUIRequest) {

		return bl.createProvider(providerUIRequest);

	}

    @GetMapping("/getReferringProviderDetails/{npiNumber}")
    public String getReferringProviderDetails(@PathVariable String npiNumber)
    {
        return bl.searchProvider(npiNumber);
    }
    @GetMapping("/getProviderDetail/{providerId}")
    public ProviderUiResponse getProviderDetail(@PathVariable Long providerId){
        return bl.getProviderDetail(providerId);
    }

    @GetMapping("/getAllProviderDetails")
    public List<ProviderUiResponse> getProviderDetails(){
       return bl.getAllProviderDetails();
    }


    @GetMapping("/getAllSpeciality")
    public List<String> getAllSpeciality(){
       return bl.getAllSpeciality();
    }



    @GetMapping("getProvidersBySpeciality/{speciality}")
    public List<ProviderUiResponse> getProvidersBySpeciality(@PathVariable String speciality){
       return bl.getProvidersBySpeciality(speciality);
    }
    
    @PostMapping("/addProviderWorkingSchedule")
    public Long addProviderWorkingSchedule(@RequestBody ProviderWorkingScheduleRequest scheduleRequest) {
        return bl.addProviderWorkingSchedule(scheduleRequest);
    }
    
    @PostMapping("/getProviderWorkingScheduleByDate")
    public List<ProviderWorkingScheduleResponse> getProviderWorkingSchedule(@RequestBody GetProviderWorkingScheduleUIReq request) {
       // return bl.getProviderWorkingSchedule(request);
    	return bl.getProviderScheduleV2(request);
    }


    @GetMapping("/getProviderSchedule/{providerId}")
    public List<ProviderWorkingScheduleResponse> getProviderSchedule(@PathVariable Long providerId){
       return bl.getProviderSchedule(providerId);
    }
}

