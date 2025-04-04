package Models;

import java.time.LocalDate;

public class Doctor extends Person implements Cloneable{

    protected EpicCode code;
    protected LocalDate hireDate;
    protected String specialty;

    public Doctor(String firstName,
                  String lastName,
                  LocalDate birthDate,
                  String dui,
                  EpicCode code,
                  LocalDate hireDate,
                  String specialty) {
        super(firstName, lastName, birthDate, dui);
        this.code = code;
        this.hireDate = hireDate;
        this.specialty = specialty;
    }

    public EpicCode getCode() {
        return code;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Doctor doctor)) return false;
//        if (!super.equals(o)) return false;

        return getCode().equals(doctor.getCode());
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "code=" + code +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", hireDate=" + hireDate +
                ", dui='" + dui + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public Doctor clone() {
        Doctor clone = (Doctor) super.clone();
        clone.code = code.clone();
        clone.hireDate = hireDate;
        clone.specialty = specialty;
        return clone;
    }
}
