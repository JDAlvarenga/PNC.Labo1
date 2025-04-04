package Services;

import Models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AppointmentService {
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentService() {
    }

    public List<Appointment> getAppointments() {
        return appointments.stream().toList();
    }

    public List<Appointment> getDoctorAppointments(Doctor doctor)
    {
        return appointments.stream().filter(a -> a.getDoctor().equals(doctor)).toList();
    }
    

    public boolean add(Appointment apt)
    {
        if (appointmentsAt(apt.getDateTime()).anyMatch(app ->
                app.getDoctor().equals(apt.getDoctor()) ||
                app.getPatient().equals(apt.getPatient())
        ))
        { return false; }

        appointments.add(apt);
        return true;
    }

    // fails if appointment does not exist or if there are conflicting appointments at the new time
    public boolean updateDate(Appointment apt, LocalDateTime newDate)
    {
        var idx = appointments.indexOf(apt);
        if (idx == -1) return false;

        // There are conflicting appointments
        if (!appointmentsAt(newDate).allMatch(app ->
                app.equals(apt) ||
                !app.getDoctor().equals(apt.getDoctor()) ||
                !app.getPatient().equals(apt.getPatient())
        ))
            return false;

        appointments.get(idx).setDateTime(newDate);
        return true;
    }

    public boolean updateState(Appointment apt, AppointmentState newState)
    {
        var idx = appointments.indexOf(apt);
        if (idx == -1) return false;

        appointments.get(idx).setState(newState);
        return true;
    }

    // Stream of appointments that overlap with 'time' and an hour after
    public Stream<Appointment> appointmentsAt(LocalDateTime startTime)
    {
        var endTime = startTime.plusHours(1);

        return appointments.stream().filter(apt -> {
            var aStart = apt.getDateTime();
            var aEnd = aStart.plusHours(1);

            // appointments are at different times
            if (startTime.equals(aEnd) || startTime.isAfter(aEnd)) return false;
            if (endTime.equals(aStart) || endTime.isBefore(aStart)) return false;

            return true;
        });
    }

    //TODO: Exclude canceled appointments from conflicts
    //TODO: Validate trying to un-cancel an appointment

}
