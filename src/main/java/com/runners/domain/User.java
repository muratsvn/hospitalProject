package com.runners.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_User")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String firstName;

    @NotBlank
    @NotNull
    @Transient //tabloda görünmesin diye
    @Column(unique = true)
    @Size(min = 5, max=5, message = "TcNo '${validatedValue}' must be five characters exactly !")
    private String tcNo;

    @Column(nullable = false,unique = true)
    @NotBlank
    private String userName;

    @Column(nullable = false)
    private String password;

    @JoinTable(name="t_user_role", joinColumns = @JoinColumn(name = "t_user"),
            inverseJoinColumns =@JoinColumn(name = "t_role"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;




















}
