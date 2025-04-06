package Models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Appointment implements Cloneable {
    private final UUID id = UUID.randomUUID(); // ID único
    private Doctor doctor;
    private Patient patient;
    private String specialty;
    private LocalDateTime dateTime;
    private AppointmentState state;

    // ✅ Getter del ID
    public UUID getId() {
        return id;
    }

    public Appointment(Doctor doctor, Patient patient, String specialty, LocalDateTime dateTime) {
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

    @Override
    public Appointment clone() {
        try {
            Appointment clone = (Appointment) super.clone();
            clone.doctor = doctor.clone();
            clone.patient = patient.clone();
            clone.specialty = specialty;
            clone.dateTime = dateTime;
            clone.state = state;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(getDoctor(), that.getDoctor()) &&
                Objects.equals(getPatient(), that.getPatient()) &&
                Objects.equals(getDateTime(), that.getDateTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getDoctor());
        result = 31 * result + Objects.hashCode(getPatient());
        result = 31 * result + Objects.hashCode(getDateTime());
        return result;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctor=" + doctor.getFirstName() + " " + doctor.getLastName() +
                ", patient=" + patient.getFirstName() + " " + patient.getLastName() +
                ", specialty='" + specialty + '\'' +
                ", dateTime=" + dateTime +
                ", state=" + state +
                '}';
    }
}
