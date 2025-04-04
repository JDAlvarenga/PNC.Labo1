package Models;

import java.time.LocalDate;

public class Patient extends Person implements Cloneable{
    public Patient(String firstName, String lastName, LocalDate birthDate, String dui) {
        super(firstName, lastName, birthDate, dui);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dui='" + dui + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public Patient clone() {
        return (Patient) super.clone();
    }
}
