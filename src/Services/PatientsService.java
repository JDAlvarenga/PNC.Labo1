package Services;

import Models.Patient;

import java.util.HashSet;
import java.util.List;

public class PatientsService {
    private final HashSet<Patient> patients = new HashSet<>();

    public PatientsService() {}

    public List<Patient> getPatients() {
        return patients.stream().map(Patient::clone).toList();
    }

    public boolean add(Patient patient) {
        return patients.add(patient.clone());
    }

    public boolean update(Patient patient) {
        if (!patients.contains(patient)) return false;

        patients.remove(patient);
        patients.add(patient.clone());
        return true;
    }

    public boolean remove(Patient patient) {
        patients.remove(patient);
        return true;
    }

    // Codigo para buscar un paciente por su DUI
    public Patient findByDui(String dui) {
        for (Patient p : getPatients()) {
            if (p.getDui().equalsIgnoreCase(dui)) {
                return p.clone();
            }
        }
        return null;
    }
}
