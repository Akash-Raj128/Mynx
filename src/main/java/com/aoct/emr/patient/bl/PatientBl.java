package com.aoct.emr.patient.bl;

import com.aoct.emr.appointment.UiResponse.AppointmentUiResponse;
import com.aoct.emr.appointment.bl.AppointmentBl;
import com.aoct.emr.appointment.entity.AppointmentEntity;
import com.aoct.emr.appointment.service.AppointmentService;
import com.aoct.emr.appointment.utility.AppointmentHelper;
import com.aoct.emr.patient.entity.Allergy;
import com.aoct.emr.patient.entity.PatientEntity;
import com.aoct.emr.patient.entity.PatientVitals;
import com.aoct.emr.patient.entity.PrescriptionEntity;
import com.aoct.emr.patient.service.AllergyService;
import com.aoct.emr.patient.service.PatientService;
import com.aoct.emr.patient.service.PatientVitalsService;
import com.aoct.emr.patient.service.PrescriptionService;
import com.aoct.emr.patient.uiRequest.AllergyUiRequest;
import com.aoct.emr.patient.uiRequest.PatientUiRequest;
import com.aoct.emr.patient.uiRequest.PatientVitalsUiRequest;
import com.aoct.emr.patient.uiRequest.PrescriptionUiRequest;
import com.aoct.emr.patient.uiResponse.AllergyUiResponse;
import com.aoct.emr.patient.uiResponse.PatientUiResponse;
import com.aoct.emr.patient.uiResponse.PatientVitalsUiResponse;
import com.aoct.emr.patient.uiResponse.PrescriptionUiResponse;
import com.aoct.emr.patient.utility.AllergyHelper;
import com.aoct.emr.patient.utility.PatientHelper;
import com.aoct.emr.patient.utility.PatientVitalsHelper;
import com.aoct.emr.patient.utility.PrescriptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PatientBl {


    @Autowired
    PatientService service;

    @Autowired
    AppointmentBl appointmentBl;

    @Autowired
    AllergyService allergyService;

    @Autowired
    PatientVitalsService patientVitalsService;

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    AppointmentService appointmentService;





//    @Autowired
//    ReadJsonFileService jsonService;
//    @Autowired
//    ExternalService externalService;



    // @Autowired

    //ReferringProviderService referringService;

    public Long createPatient(PatientUiRequest req)

    {

        PatientEntity p = PatientHelper.convertPatientRequest(req);

//

        Long patId=service.createPatient(p);

        return patId;

        //return null;

    }
    public PatientUiResponse getPatientDetails(Long patientId) {

        PatientEntity p=service.getPatientDetails(patientId);



        PatientUiResponse response=PatientHelper.convertToPatientUiResponse(p);

       List<AppointmentUiResponse> appointmentUiResponseLIst= appointmentBl.getAppointmentByPatientId(patientId);
       response.setAppointments(appointmentUiResponseLIst);

        //  ReferringProvider referringProvider = referringService.getReferringProviderById(p.getNpiId());

        // ReferringProviderUiResponse referringProviderUiResponse=PatientHelper.convertToReferringProviderUiResponse(referringProvider);



        //response.setReferringProvider(referringProviderUiResponse);

        // response.setReferringProvider(new com.mynx.mica.patient.uiResponse.ReferringProviderUiResponse());


        return response;

    }



    public List<PatientUiResponse> getAllPatients() {

        // TODO Auto-generated method stub

        List<PatientEntity> patients =service.getAllPatients();

        List<PatientUiResponse> listOfPatients=PatientHelper.convertToPatientListUiResponse(patients);

        return listOfPatients;

    }

    public void savePatientPicture(){



    }

    public Long addAllergy(AllergyUiRequest allergyUiRequest) {
        Allergy a= AllergyHelper.convertAllergyUiRequest(allergyUiRequest);

        Long patientAllergyId=allergyService.addAllergy(a);
        return patientAllergyId;
    }

    public List<AllergyUiResponse> getPatientAllergy(Long patientId) {
        List<Allergy> allergyList=allergyService.getPatientAllergy(patientId);
        List<AllergyUiResponse> responses=AllergyHelper.convertAllergyListUiResponse(allergyList);
        return responses;
    }

    public Long addVitalsForPatient(PatientVitalsUiRequest patientVitalsUiRequest) {

        // String bmi=PatientVitalsHelper.bmiCalculator(patientVitalsUiRequest.getHeight(),patientVitalsUiRequest.getWeight());
        // patientVitalsUiRequest.setBmi(bmi);

        PatientVitals v= PatientVitalsHelper.convertPatientVitalsUiRequest(patientVitalsUiRequest);

        Long vitalsId=patientVitalsService.addVitalsForPatient(v);
        return vitalsId;
    }

    public Map<LocalDate,PatientVitalsUiResponse> getVitalsForPatient(Long patientId) {
        List<PatientVitals> vitalslist=patientVitalsService.getVitalsForPatient(patientId);
        Map<LocalDate,PatientVitalsUiResponse> responses=PatientVitalsHelper.convertPatientVitalsListUiResponse(vitalslist);

         List<Map.Entry<LocalDate, PatientVitalsUiResponse>> entryList = new ArrayList<>(responses.entrySet());

        Collections.sort(entryList, (entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()));

        Map<LocalDate, PatientVitalsUiResponse> sortedMap = entryList.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        return sortedMap;

    }

    public Map<Integer, String> getAllAllergy() {
        Map<Integer,String> hm=new HashMap<>();
        hm.put(1,"Penicillin");
        hm.put(2,"Pollen");
        hm.put(3,"Sand");
        hm.put(4,"FS Shampoo");
        return hm;


    }

    public Long addPrescription(PrescriptionUiRequest prescription) {
        PrescriptionEntity p= PrescriptionHelper.convertPrescriptionUiRequest(prescription);
        Long prescriptionId= prescriptionService.addPrescription(p);
        return prescriptionId;
    }

    public List<PrescriptionUiResponse> getPrescriptionByPatientId(Long patientId) {
        List<PrescriptionEntity> prescriptions=prescriptionService.getPrescriptionByPatientId(patientId);
        List<PrescriptionUiResponse> listOfPrescription=PrescriptionHelper.convertListOfPrescriptionUiResponse(prescriptions);
        return listOfPrescription;
    }

    public PrescriptionUiResponse getPrescriptionById(Long prescriptionId) {
        PrescriptionEntity p=prescriptionService.getPrescriptionById(prescriptionId);
        PrescriptionUiResponse response=PrescriptionHelper.convertPrescriptionUiresponse(p);
        return response;

    }

    public List<AppointmentUiResponse> getAppointmentByPatientId(Long patientId) {
        List<AppointmentEntity> appointments = appointmentService.getAppointmentByPatientId(patientId);
        List<AppointmentUiResponse> responses = appointments.stream()
                .map(AppointmentHelper::convertToBookAppointmentUiResponse)
                .collect(Collectors.toList());

        return responses;
    }

    public void deletePrescriptionById(Long prescriptionId) {
        prescriptionService.deletePrescriptionById(prescriptionId);
    }

    public List<PrescriptionUiResponse> getAllPrescriptions() {
        List<PrescriptionEntity> prescriptions=prescriptionService.getAllPrescriptions();

        List<PrescriptionUiResponse> response=PrescriptionHelper.convertListOfPrescriptionUiResponse(prescriptions);
        return response;
    }



	/*	will integrate this once the doctor module is ready

	 * public List<ReferringProviderUiResponse> getNpdiDetails() {





			List<ReferringProviderUiResponse> referringProviderList = new ArrayList<ReferringProviderUiResponse>();



			List<ReferringProvider> listOfRefferal= referringService.getAllReferral();

			for(ReferringProvider referral:listOfRefferal)

			{

				ReferringProviderUiResponse convertToReferringProviderUiResponse = PatientHelper.convertToReferringProviderUiResponse(referral);

				referringProviderList.add(convertToReferringProviderUiResponse);

			}



			return referringProviderList;

		}*/



//    public String searchProvider(String npiNumber) {
//
//        // TODO Auto-generated method stub
//
//        String result=externalService.callApi(npiNumber);
//
//
//
//        return null;
//
//    }



//    public String addReferringProvider(ReferringProviderUiRequest req) {
//
//        // TODO Auto-generated method stub
//
//		/*
//
//			ReferringProvider referringProvider =PatientHelper.conertFromReferringProviderUiRequest(req);
//
//			return referringService.addReferringProvider(referringProvider);
//
//		*/
//
//        return null;
//
//    }
}

