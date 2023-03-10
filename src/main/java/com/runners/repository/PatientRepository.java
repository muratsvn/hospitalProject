package com.runners.repository;

import com.runners.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    boolean existsByTcNo(String tcNo);



    Optional<Patient> findByTcNo(String tcNo);
}
