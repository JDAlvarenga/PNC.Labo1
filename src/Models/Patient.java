package Models;

import java.time.LocalDate;

public class Patient extends Person{
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
}
