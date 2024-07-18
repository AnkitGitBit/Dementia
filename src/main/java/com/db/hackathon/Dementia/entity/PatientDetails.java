//package com.db.hackathon.Dementia.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "patientdetails")
//public class PatientDetails {
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long pDetailId;
//    
//    @Column(nullable=false)
//    private Long pId;
//
//    @Column(nullable=false)
//    private Medication medication;
//
//    @Column(nullable=false, unique=true)
//    private HealthTracking healthTracking;
//
//    @Column(nullable=false)
//    private Schedules schedules;
//    
//    @Column(nullable=false)
//    private Notes notes;
//    
//    @Column(nullable=false)
//    private Resources resources;
//    
//    @Column(nullable=false)
//    private String emergencycontact;
//
//}
