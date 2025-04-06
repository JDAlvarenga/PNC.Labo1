package Models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Doctor implements Cloneable {
    private final UUID id = UUID.randomUUID(); // ID único que se genera automaticamente
    private EpicCode code;
    private String firstName;
    private String lastName;
    private String dui;
    private LocalDate birthDate;
    private LocalDate recruitedDate;
    private String specialty;

    // Datos del doctor
    public Doctor(EpicCode code, String firstName, String lastName, String dui, LocalDate birthDate, LocalDate recruitedDate, String specialty) {
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dui = dui;
        this.birthDate = birthDate;
        this.recruitedDate = recruitedDate;
        this.specialty = specialty;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public EpicCode getCode() {
        return code;
    }

    public void setCode(EpicCode code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRecruitedDate() {
        return recruitedDate;
    }

    public void setRecruitedDate(LocalDate recruitedDate) {
        this.recruitedDate = recruitedDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public Doctor clone() {
        try {
            Doctor clone = (Doctor) super.clone();
            clone.code = code;
            clone.firstName = firstName;
            clone.lastName = lastName;
            clone.dui = dui;
            clone.birthDate = birthDate;
            clone.recruitedDate = recruitedDate;
            clone.specialty = specialty;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Doctor that)) return false;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", code=" + code +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dui='" + dui + '\'' +
                ", birthDate=" + birthDate +
                ", recruitedDate=" + recruitedDate +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
