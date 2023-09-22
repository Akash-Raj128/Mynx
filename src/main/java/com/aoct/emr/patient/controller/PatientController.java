package com.aoct.emr.patient.controller;

import com.aoct.emr.appointment.UiResponse.AppointmentUiResponse;
import com.aoct.emr.patient.bl.PatientBl;
import com.aoct.emr.patient.uiRequest.AllergyUiRequest;
import com.aoct.emr.patient.uiRequest.PatientUiRequest;
import com.aoct.emr.patient.uiRequest.PatientVitalsUiRequest;
import com.aoct.emr.patient.uiRequest.PrescriptionUiRequest;
import com.aoct.emr.patient.uiResponse.AllergyUiResponse;
import com.aoct.emr.patient.uiResponse.PatientUiResponse;
import com.aoct.emr.patient.uiResponse.PatientVitalsUiResponse;
import com.aoct.emr.patient.uiResponse.PrescriptionUiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/patient")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class PatientController {

	@Autowired
	PatientBl bl;


	@Operation(summary = "This endpoint is used to create patient")
	@PostMapping("/createPatient")
	public Long createPatient(@RequestBody PatientUiRequest patientUiRequest) {

		return bl.createPatient(patientUiRequest);

	}
	@Operation(summary = "This endpoint is used to view patient details")
	@GetMapping("/getPatientDetails/{patientId}")
	public PatientUiResponse getPatientDetails(@PathVariable Long patientId) {



		return bl.getPatientDetails(patientId);

	}
	@GetMapping("/getAllPatients")
	public List<PatientUiResponse> getAllPatients() {

		return bl.getAllPatients();

	}

	//adding allergies associated with patient

	@PostMapping("/addAllergy")
	public Long addPatientAllergy(@RequestBody AllergyUiRequest allergyUiRequest){
		return bl.addAllergy(allergyUiRequest);
	}

	@GetMapping("/getAllergyByPatientId/{patientId}")
	public List<AllergyUiResponse> getAllergyByPatientId(@PathVariable Long patientId){
		return bl.getPatientAllergy(patientId);
	}

	@GetMapping ("/getAllAllergy")
	public Map<Integer,String> getAllAllergy(){
		return  bl.getAllAllergy();
	}

	//adding vitals for Patient

	@PostMapping("/addVitalsForPatient")
	public Long addVitalsForPatient(@RequestBody PatientVitalsUiRequest patientVitalsUiRequest){
	return bl.addVitalsForPatient(patientVitalsUiRequest);
	}

	@GetMapping("/getVitalsForPatient/{patientId}")
	public Map<LocalDate,PatientVitalsUiResponse> getVitalsForPatient(@PathVariable Long patientId){
		return bl.getVitalsForPatient(patientId);
	}

	//adding prescription for patient

	@PostMapping("/addPrescription")
	public Long addPrescription(@RequestBody PrescriptionUiRequest prescription){
		return bl.addPrescription(prescription);
	}

	@GetMapping("/getPrescriptionByPatientId/{patientId}")
	public List<PrescriptionUiResponse> getPrescriptionByPatientId(@PathVariable Long patientId){
		return bl.getPrescriptionByPatientId(patientId);
	}
	@GetMapping("/getPrescriptionById/{prescriptionId}")
	public PrescriptionUiResponse getPrescriptionById(@PathVariable Long prescriptionId){
		return bl.getPrescriptionById(prescriptionId);
	}
	@DeleteMapping("/deletePrescriptionById/{prescriptionId}")
	public void deletePrescription(@PathVariable Long prescriptionId){
		bl.deletePrescriptionById(prescriptionId);
	}

	@GetMapping("/getAllPrescriptions")
	public List<PrescriptionUiResponse> getAllPrescriptions(){
		return bl.getAllPrescriptions();
	}
	@GetMapping("/getAllAppointmentByPatientId/{patientId}")
	public List<AppointmentUiResponse> getAllAppointmentsByPatientId(@PathVariable Long patientId) {

		List<AppointmentUiResponse> appointments = bl.getAppointmentByPatientId(patientId);
		appointments.sort(Comparator.comparing(AppointmentUiResponse::getSpeciality));
		return appointments;
	}

	@GetMapping("/getAllAppointmentByPatientIdDateSorted/{patientId}")
	public List<AppointmentUiResponse> getAllAppointmentsByPatientIdDateSorted(@PathVariable Long patientId) {
		List<AppointmentUiResponse> appointments = bl.getAppointmentByPatientId(patientId);
		appointments.sort(Comparator.comparing(AppointmentUiResponse::getScheduleDate));
		return appointments;
	}


}
