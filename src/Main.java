import Models.*;
import Services.AppointmentsService;
import Services.DoctorsService;
import Services.PatientsService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        var d1 = new Doctor("Dr.", "Mundo", LocalDate.now().minusYears(40), "1865156-5", EpicCode.NewCode(), LocalDate.now(), "Saw");

        var d2 = d1.clone();
        d2.setFirstName("Not");



        var doctors = new DoctorsService();

        if (doctors.add(d1)) System.out.println("Added: "+ d1);
        else System.out.println("Failed to add: " + d1);

        // fails to add doctor with same code
        if (doctors.add(d2)) System.out.println("Added: "+ d2);
        else System.out.println("Failed to add: " + d2);

        // update doctor with new firstname
        if (doctors.update(d2)) System.out.println("Updated: "+ d2);
        else System.out.println("Failed to update: " + d2);

        // modifying the d2 object does not change the doctors in the service
        d2.setFirstName("Real");

        System.out.println();
        System.out.println("Registered doctors");
        for(var doc: doctors.getDoctors())
            System.out.println(doc);


        System.out.println();
        System.out.println();
        System.out.println();


        Patient p1 = new Patient("Patient", "Zero", LocalDate.now().minusYears(26), "68431571-3");

        Patient p2 = new Patient("Patient", "-One", LocalDate.now().minusYears(16), "68431575-7");

        var patients = new PatientsService();

        if (patients.add(p1)) System.out.println("Added: "+ p1);
        else System.out.println("Failed to add: " + p1);


        if (patients.add(p2)) System.out.println("Added: "+ p2);
        else System.out.println("Failed to add: " + p2);


        // modifying the p2 object does not change the patients in the service
        p2.setFirstName("Impatient");

        System.out.println();
        System.out.println("Registered patients");
        for(var patient: patients.getPatients())
            System.out.println(patient);

        System.out.println();
        System.out.println();
        System.out.println();


        var appointments = new AppointmentsService();

        var now = LocalDateTime.now();

        var apt1 = new Appointment(
                d1,
                p1,
                d1.getSpecialty(),
                now.plusMinutes(60));

        if (appointments.add(apt1)) System.out.println("Added :" + apt1);
        else System.out.println("Failed to add: " + apt1);

        // Try to schedule at same time
        var apt2 = new Appointment(
                d1,
                p2,
                d1.getSpecialty(),
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