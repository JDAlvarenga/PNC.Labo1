package Services;

import Models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class AppointmentsService {
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentsService() {}

    public List<Appointment> getAppointments() {
        return appointments.stream().map(Appointment::clone).toList();
    }

    public List<Appointment> getDoctorAppointments(Doctor doctor) {
        return appointments.stream().filter(a -> a.getDoctor().equals(doctor)).toList();
    }

    public boolean add(Appointment apt) {
        var hour = apt.getDateTime().getHour();
        if (hour < 8 || hour > 16) return false;

        if (appointmentsAt(apt.getDateTime()).anyMatch(app ->
                app.getDoctor().equals(apt.getDoctor()) ||
                        app.getPatient().equals(apt.getPatient())
        )) {
            return false;
        }
        appointments.add(apt);
        return true;
    }

    public boolean updateDate(Appointment apt, LocalDateTime newDate) {
        var idx = appointments.indexOf(apt);
        if (idx == -1) return false;

        if (!appointmentsAt(newDate).allMatch(app ->
                app.equals(apt) ||
                        !app.getDoctor().equals(apt.getDoctor()) ||
                        !app.getPatient().equals(apt.getPatient())
        )) {
            return false;
        }

        appointments.get(idx).setDateTime(newDate);
        return true;
    }

    public boolean updateState(Appointment apt, AppointmentState newState) {
        var idx = appointments.indexOf(apt);
        if (idx == -1) return false;

        if (appointments.get(idx).getState() == newState) return true;

        if (appointments.get(idx).getState() == AppointmentState.Canceled) {
            if (appointmentsAt(apt.getDateTime()).anyMatch(app ->
                    app.getDoctor().equals(apt.getDoctor()) ||
                            app.getPatient().equals(apt.getPatient())
            )) return false;
        }

        appointments.get(idx).setState(newState);
        return true;
    }

    public Stream<Appointment> getAppointmentsAt(LocalDateTime startTime) {
        return appointmentsAt(startTime).map(Appointment::clone);
    }

    public Stream<Appointment> getAppointmentsAt(LocalDateTime startTime, boolean excludeCanceled) {
        return appointmentsAt(startTime, excludeCanceled).map(Appointment::clone);
    }

    protected Stream<Appointment> appointmentsAt(LocalDateTime startTime) {
        return appointmentsAt(startTime, true);
    }

    protected Stream<Appointment> appointmentsAt(LocalDateTime startTime, boolean excludeCanceled) {
        var endTime = startTime.plusHours(1);

        return appointments.stream().filter(apt -> {
            if (excludeCanceled && apt.getState() == AppointmentState.Canceled) return false;

            var aStart = apt.getDateTime();
            var aEnd = aStart.plusHours(1);

            if (startTime.equals(aEnd) || startTime.isAfter(aEnd)) return false;
            if (endTime.equals(aStart) || endTime.isBefore(aStart)) return false;

            return true;
        });
    }

    // Codigo para establecer hora automática sin conflictos
    public LocalDateTime asignarHoraDisponible(Doctor doctor, Patient patient, LocalDate fecha) {
        LocalTime horaInicio = LocalTime.of(8, 0);

        for (int i = 0; i <= 8; i++) {
            LocalTime hora = horaInicio.plusHours(i);
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

            boolean conflicto = getAppointments().stream().anyMatch(a ->
                    a.getDateTime().equals(fechaHora) &&
                            (a.getDoctor().equals(doctor) || a.getPatient().equals(patient))
            );

            if (!conflicto) {
                return fechaHora;
            }
        }
        return null;
    }

    // Codigo para obbtener citas por fecha específica
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return getAppointments().stream()
                .filter(a -> a.getDateTime().toLocalDate().equals(date))
                .toList();
    }

    // Codigo para obtener citas por código de doctor
    public List<Appointment> getAppointmentsByDoctor(String code) {
        return getAppointments().stream()
                .filter( a -> a.getDoctor().getCode().toString().equalsIgnoreCase(code))
                .toList();
    }

    // Codigo para cancelar cita por UUID
    public boolean cancelarCita(UUID id) {
        for (Appointment a : appointments) {
            if (a.getId().equals(id)) {
                return updateState(a, AppointmentState.Canceled);
            }
        }
        return false;
    }
}
