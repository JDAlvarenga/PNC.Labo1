package Services;

import Models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AppointmentsService {
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentsService() {
    }

    public List<Appointment> getAppointments() {
        return appointments.stream().map(Appointment::clone).toList();
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

        if (appointments.get(idx).getState() == newState) return true;

        // it's possible for a new appointment to be scheduled at this time as this was canceled
        if (appointments.get(idx).getState() == AppointmentState.Canceled)
        {
            // There are conflicting appointments
            if (appointmentsAt(apt.getDateTime()).anyMatch(app ->
                    app.getDoctor().equals(apt.getDoctor()) ||
                    app.getPatient().equals(apt.getPatient())
            )) return false;
        }
        appointments.get(idx).setState(newState);
        return true;
    }

    // public versions that return clones to avoid modifying internal state
    public Stream<Appointment> getAppointmentsAt(LocalDateTime startTime) {return appointmentsAt(startTime).map(Appointment::clone);}
    public Stream<Appointment> getAppointmentsAt(LocalDateTime startTime, boolean excludeCanceled) {return appointmentsAt(startTime, excludeCanceled).map(Appointment::clone);}


    // Stream of appointments that overlap with 'time' and an hour after
    protected Stream<Appointment> appointmentsAt(LocalDateTime startTime)
    {
        return appointmentsAt(startTime, true);
    }
    protected Stream<Appointment> appointmentsAt(LocalDateTime startTime, boolean excludeCanceled)
    {
        var endTime = startTime.plusHours(1);

        return appointments.stream().filter(apt -> {
            if (excludeCanceled && apt.getState() == AppointmentState.Canceled) return false;

            var aStart = apt.getDateTime();
            var aEnd = aStart.plusHours(1);

            // appointments are at different times
            if (startTime.equals(aEnd) || startTime.isAfter(aEnd)) return false;
            if (endTime.equals(aStart) || endTime.isBefore(aStart)) return false;

            return true;
        });
    }

}
