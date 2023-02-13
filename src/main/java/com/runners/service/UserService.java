package com.runners.service;


import com.runners.domain.Doctor;
import com.runners.domain.Patient;
import com.runners.domain.Role;
import com.runners.domain.User;
import com.runners.domain.enums.UserRole;
import com.runners.dto.UserDto;
import com.runners.exception.ResourceNotFoundException;
import com.runners.repository.PatientRepository;
import com.runners.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;


    public void saveUser(UserDto userDto) {

        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setUserName(userDto.getUserName());

        String pwrd = userDto.getPassword();
        String encodePwrd = passwordEncoder.encode(pwrd);

        user.setPassword(encodePwrd);

        Role role = roleService.getByRole(UserRole.ROLE_ADMIN);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);



        boolean doctorexists = doctorService.existsDoctor(userDto.getTcNo());
        boolean patientexists = patientService.existsPatient(userDto.getTcNo());

        if (doctorexists){
            Doctor doctor = doctorService.getDoctorByTcNo(userDto.getTcNo());
            user.setDoctor(doctor);
        } else
            if (patientexists) {
            Patient patient = patientService.getPatientByTc(userDto.getTcNo());
            user.setPatient(patient);
        }else throw new ResourceNotFoundException("Patient and Doctor not found by tcno : " + userDto.getTcNo());



        userRepository.save(user);





    }
}
