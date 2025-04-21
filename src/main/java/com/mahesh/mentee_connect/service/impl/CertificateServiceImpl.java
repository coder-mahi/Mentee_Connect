package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.*;
import com.mahesh.mentee_connect.repository.*;
import com.mahesh.mentee_connect.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Override
    public Certificate saveCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    @Override
    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id).orElse(null);
    }

    @Override
    public Certificate updateCertificate(Long id, Certificate certificate) {
        certificate.setId(id);
        return certificateRepository.save(certificate);
    }

    @Override
    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }
}
