package Services;

import Models.Doctor;
import java.util.HashSet;
import java.util.List;

public class DoctorsService {
    private final HashSet<Doctor> doctors = new HashSet<>();

    public List<Doctor> getDoctors() {
        return doctors.stream().map(Doctor::clone).toList();
    }

    public boolean add(Doctor doctor) {
        return doctors.add(doctor.clone());
    }

    public boolean update(Doctor doctor) {
        if (!doctors.contains(doctor)) return false;

        doctors.remove(doctor);
        doctors.add(doctor.clone());
        return true;
    }

    public boolean remove(Doctor doctor) {
        return doctors.remove(doctor);
    }

    //  Codigo para buscar un doctor por su código
    public Doctor findByCode(String code) {
        for (Doctor d : getDoctors()) {
            if (d.getCode().toString().equalsIgnoreCase(code)) {

                return d.clone();
            }
        }
        return null;
    }
}
