package Models;

import java.time.LocalDateTime;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String specialty;
    private LocalDateTime dateTime;
    private AppointmentState state;


    public Appointment(Doctor doctor, Patient patient, String specialty, LocalDateTime dateTime ) {
        this(doctor, patient, specialty, dateTime, AppointmentState.Scheduled);
    }
    public Appointment(Doctor doctor, Patient patient, String specialty, LocalDateTime dateTime, AppointmentState state) {
        this.doctor = doctor;
        this.patient = patient;
        this.specialty = specialty;
        this.dateTime = dateTime;
        this.state = state;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean setDateTime(LocalDateTime dateTime) {
        var hour = dateTime.getHour();
        if (hour < 8 || hour > 16) return false;
        this.dateTime = dateTime;
        return true;
    }

    public AppointmentState getState() {
        return state;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }


}

