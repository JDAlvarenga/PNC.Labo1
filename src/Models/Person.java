package Models;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Person implements Cloneable{
    protected String firstName;
    protected String lastName;
    protected String dui;
    protected LocalDate birthDate;


    public Person(String firstName, String lastName,LocalDate birthDate, String dui) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setDui(dui);
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

    protected void setDui(String dui) {
        // less than 18 years old
        this.dui = this.isMinor()? "00000000-0": dui;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    protected void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    protected boolean isMinor()
    {
        return this.birthDate.isAfter(BIG_HUMAN);
    }
    // 18+ years old
    private static final LocalDate BIG_HUMAN = LocalDate.now().minusYears(18);

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;

        return Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getDui(), person.getDui()) && Objects.equals(getBirthDate(), person.getBirthDate());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getFirstName());
        result = 31 * result + Objects.hashCode(getLastName());
        result = 31 * result + Objects.hashCode(getDui());
        result = 31 * result + Objects.hashCode(getBirthDate());
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dui='" + dui + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public Person clone() {
        try {
            Person clone = (Person) super.clone();
            clone.firstName = firstName;
            clone.lastName = lastName;
            clone.dui = dui;
            clone.birthDate = birthDate;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
