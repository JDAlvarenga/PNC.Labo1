package Services;

import Models.Doctor;

import java.util.HashSet;
import java.util.List;

public class DoctorsService {
    private final HashSet<Doctor> doctors = new HashSet<>();

    public DoctorsService() {}

    public List<Doctor> getDoctors()
    {
        return doctors.stream().toList();
    }

    public boolean add(Doctor doctor)
    {
        return doctors.add(doctor.clone());
    }

    public boolean update(Doctor doctor)
    {
        if(!doctors.contains(doctor)) return false;

        doctors.remove(doctor);
        doctors.add(doctor.clone());
        return true;

    }

    public boolean remove(Doctor doctor)
    {
        doctors.remove(doctor);
        return true;
    }
}
