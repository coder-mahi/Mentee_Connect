package com.mahesh.mentee_connect.service;
import java.util.*;
import com.mahesh.mentee_connect.model.*;

public interface CertificateService {
    Certificate saveCertificate(Certificate certificate);
    List<Certificate> getAllCertificates();
    Certificate getCertificateById(Long id);
    Certificate updateCertificate(Long id, Certificate certificate);
    void deleteCertificate(Long id);
}
