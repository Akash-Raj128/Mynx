package com.aoct.emr.patient.service;

import com.aoct.emr.patient.entity.Allergy;
import com.aoct.emr.patient.entity.PrescriptionEntity;
import com.aoct.emr.patient.repository.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    PrescriptionRepo repo;
    public  Long addPrescription(PrescriptionEntity p) {

        PrescriptionEntity prescription=repo.save(p);
        if(prescription==null){
            return null;
        }
        else{
            return prescription.getPrescriptionId();
        }
    }

    public List<PrescriptionEntity> getPrescriptionByPatientId(Long patientId) {
        return repo.getPrescriptionByPatientId(patientId);
    }

    public PrescriptionEntity getPrescriptionById(Long prescriptionId) {
        return repo.getReferenceById(prescriptionId);
    }

    public void deletePrescriptionById(Long prescriptionId) {
        repo.deleteById(prescriptionId);
    }

    public List<PrescriptionEntity> getAllPrescriptions() {
        return repo.findAll();
    }
}
