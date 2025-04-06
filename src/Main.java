import Models.*;
import Services.AppointmentsService;
import Services.DoctorsService;
import Services.PatientsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoctorsService doctorsService = new DoctorsService();
        PatientsService patientsService = new PatientsService();
        AppointmentsService appointmentsService = new AppointmentsService();

        boolean running = true;

        while (running) {
            System.out.println("\n--- BIENVENIDO AL SISTEMA DEL DOCTOR MUNDO ---");
            System.out.println("1. Agregar nuevo doctor");
            System.out.println("2. Agregar nuevo paciente");
            System.out.println("3. Agendar nueva cita");
            System.out.println("4. Ver todas las citas");
            System.out.println("5. Ver citas por fecha");
            System.out.println("6. Ver citas por código de doctor");
            System.out.println("7. Cancelar cita");
            System.out.println("8. Mundo salva vidas");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    // Agregar nuevo doctor
                    System.out.print("Nombre: ");
                    String nombreD = scanner.nextLine();

                    System.out.print("Apellido: ");
                    String apellidoD = scanner.nextLine();

                    System.out.print("DUI: ");
                    String duiD = scanner.nextLine();

                    LocalDate nacimientoD = promptDate(scanner, "Fecha de nacimiento (YYYY-MM-DD): ");

                    LocalDate reclutado = promptDate(scanner, "Fecha de reclutamiento (YYYY-MM-DD): ");

                    System.out.print("Especialidad: ");
                    String especialidad = scanner.nextLine();

                    Doctor doctor = new Doctor(
                            EpicCode.NewCode(),
                            nombreD,
                            apellidoD,
                            duiD,
                            nacimientoD,
                            reclutado,
                            especialidad
                    );

                    if (doctorsService.add(doctor)) {
                        System.out.println(" Doctor agregado exitosamente.");
                        System.out.println(" Código épico: " + doctor.getCode());
                        System.out.println(" ID único: " + doctor.getId());
                    } else {
                        System.out.println(" No se pudo agregar al doctor :c ");
                    }
                    break;


                case 2:
                    // Agregar nuevo paciente
                    System.out.print("Nombre: ");
                    String nombreP = scanner.nextLine();
                    System.out.print("Apellido: ");
                    String apellidoP = scanner.nextLine();
                    System.out.print("DUI (00000000-0 si es menor): ");
                    String duiP = scanner.nextLine();

                    LocalDate nacimientoP = promptDate(scanner, "Fecha de nacimiento (YYYY-MM-DD): ");
                    Patient paciente = new Patient(nombreP, apellidoP, nacimientoP, duiP);
                    if (patientsService.add(paciente))
                        System.out.println("Paciente agregado exitosamente.");
                    else
                        System.out.println("No se pudo agregar al paciente.");
                    break;

                case 3:
                    // Agendar cita
                    System.out.print("DUI del paciente: ");
                    String duiPaciente = scanner.nextLine();
                    Patient pacienteCita = patientsService.findByDui(duiPaciente);
                    if (pacienteCita == null) {
                        System.out.println("Paciente no encontrado.");
                        break;
                    }
                    System.out.print("Código del doctor: ");
                    String codigoDoctor = scanner.nextLine();
                    Doctor doctorCita = doctorsService.findByCode(codigoDoctor);
                    if (doctorCita == null) {
                        System.out.println("Doctor no encontrado.");
                        break;
                    }
                    System.out.print("¿La cita es para hoy? (s/n): ");
                    boolean esHoy = scanner.nextLine().equalsIgnoreCase("s");

                    LocalDateTime fechaHora;
                    if (esHoy) {
                        LocalTime hora = promptTime(scanner, "Hora exacta (HH:mm): ");
                        fechaHora = LocalDateTime.of(LocalDate.now(), hora);
                    } else {
                        LocalDate fecha = promptDate(scanner, "Fecha (YYYY-MM-DD): ");
                        // Asignación automática de hora entre 8:00 y 16:00 sin conflictos
                        fechaHora = appointmentsService.asignarHoraDisponible(doctorCita, pacienteCita, fecha);
                        if (fechaHora == null) {
                            System.out.println("No hay horas disponibles para esa fecha.");
                            break;
                        }
                    }
                    Appointment nuevaCita = new Appointment(doctorCita, pacienteCita, doctorCita.getSpecialty(), fechaHora);
                    if (appointmentsService.add(nuevaCita))
                        System.out.println("Cita agendada correctamente.");
                    else
                        System.out.println("No se pudo agendar la cita. Verifique conflictos de horario.");
                    break;

                case 4:
                    // Mostrar todas las citas
                    for (Appointment a : appointmentsService.getAppointments()) {
                        System.out.println(a);
                    }
                    break;

                case 5:
                    // Ver citas por fecha
                    LocalDate fechaFiltro = promptDate(scanner, "Ingrese fecha (YYYY-MM-DD): ");

                    appointmentsService.getAppointmentsByDate(fechaFiltro).forEach(System.out::println);
                    break;

                case 6:
                    // Buscar citas por código de doctor
                    System.out.print("Código del doctor: ");
                    String code = scanner.nextLine();
                    appointmentsService.getAppointmentsByDoctor(code).forEach(System.out::println);
                    break;

                case 7:
                    // Cancelar cita por ID
                    System.out.print("Ingrese ID de la cita a cancelar: ");
                    String idCita = scanner.nextLine();
                    if (appointmentsService.cancelarCita(UUID.fromString(idCita)))
                        System.out.println("Cita cancelada exitosamente.");
                    else
                        System.out.println("No se encontró la cita con ese ID.");
                    break;

                case 8:
                    // Boton de "Mundo salva vidas"
                    System.out.println("\n ¡Mundo salva vidas! EL MEJOR \n");
                    break;

                case 9:
                    running = false;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static LocalDate promptDate (Scanner scanner, String msg){
        LocalDate date = null;

        while (date == null) {
            try {
                System.out.print(msg);
                date = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Intente de nuevo");
            }
        }
        return date;
    }
    private static LocalTime promptTime (Scanner scanner, String msg){
        LocalTime time = null;

        while (time == null) {
            try {
                System.out.print(msg);
                time = LocalTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de hora incorrecto. Intente de nuevo");
            }
        }
        return time;
    }
}
