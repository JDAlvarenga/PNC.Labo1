import Models.*;
import Services.AppointmentsService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Patient p1 = new Patient("Patient", "Zero", LocalDate.now().minusYears(26), "68431571-3");

        Patient p2 = new Patient("Patient", "-One", LocalDate.now().minusYears(16), "68431575-7");

        var d = new Doctor("Dr.", "Mundo", LocalDate.now().minusYears(40), "1865156-5", EpicCode.NewCode(), LocalDate.now(), "Saw");

        var appointments = new AppointmentsService();

        var now = LocalDateTime.now();

        var apt1 = new Appointment(
                d,
                p1,
                d.getSpecialty(),
                now.plusMinutes(60));

        if (appointments.add(apt1)) System.out.println("Added :" + apt1);
        else System.out.println("Failed to add: " + apt1);

        // Try to schedule at same time
        var apt2 = new Appointment(
                d,
                p2,
                d.getSpecialty(),
                now.plusMinutes(90),
                AppointmentState.Scheduled);

        if (appointments.add(apt2)) System.out.println("Added :" + apt2);
        else System.out.println("Failed to add: " + apt2);


        // Cancel first appointment
        if (appointments.updateState(apt1, AppointmentState.Canceled))
            System.out.println("Canceled: " + apt1);
        else System.out.println("Failed to Cancel: " + apt1);



        // Retry appointment 2 (success)
        if (appointments.add(apt2)) System.out.println("Added :" + apt2);
        else System.out.println("Failed to add: " + apt2);


        // Try to un cancel appointment 1 (conflicts with apt2)(failure)
        if (appointments.updateState(apt1, AppointmentState.Scheduled))
            System.out.println("Scheduled: " + apt1);
        else System.out.println("Failed to Schedule: " + apt1);


        System.out.println();
        System.out.println("Scheduled Appointments");
        for(var a : appointments.getAppointments())
        {
            System.out.println(a);
        }


    }
}