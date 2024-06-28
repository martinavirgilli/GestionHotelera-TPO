import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        // Crear los tipos de habitacion.
        Tipo simple = new Tipo("simple", 1000);
        Tipo doble = new Tipo("doble", 2000);
        Tipo familiar = new Tipo("familiar", 3000);

        // Agregar habitaciones.
        hotel.agregarHabitacion(new Habitacion(simple));
        hotel.agregarHabitacion(new Habitacion(simple));
        hotel.agregarHabitacion(new Habitacion(doble));
        hotel.agregarHabitacion(new Habitacion(familiar));

        boolean ejecutando = true;

        // Arranca el sistema y continua haciendo reservas hasta cortarlo ingresando 'salir'.
        while (ejecutando) {
            System.out.println("Ingrese los datos de la reserva (o escriba 'salir' para terminar):" + "\n");

            System.out.print("Tipo de habitación (simple, doble, familiar): ");
            String tipoHabitacion = scanner.nextLine();

            // 'equalsIgnoreCase' se usa para comparar cadenas
            // de texto sin contemplar mayúsculas y minúsculas.

            if (tipoHabitacion.equalsIgnoreCase("salir")) {
                ejecutando = false;
                break;
            }

            Tipo tipo = null;
            if (tipoHabitacion.equalsIgnoreCase("simple")) {
                tipo = simple;
            } else if (tipoHabitacion.equalsIgnoreCase("doble")) {
                tipo = doble;
            } else if (tipoHabitacion.equalsIgnoreCase("familiar")) {
                tipo = familiar;
            } else {
                System.out.println("Tipo de habitación no válido.");
                continue;
            }

            Date fechaInicio = null;
            Date fechaFin = null;
            int intentosFechas = 0;
            boolean fechasValidas = false;
//            boolean tipoValido = true;

            // Solicitar fechas de reserva y verificar disponibilidad para el tipo de habitacion.
            while (!fechasValidas && intentosFechas < 3) {
                try {
                    System.out.print("Fecha de ingreso (AAAA-MM-DD): ");
                    String fechaInicioStr = scanner.nextLine();
                    fechaInicio = parseFecha(fechaInicioStr);
                    validarFecha(fechaInicio);

                    System.out.print("Fecha de salida (AAAA-MM-DD): ");
                    String fechaFinStr = scanner.nextLine();
                    fechaFin = parseFecha(fechaFinStr);
                    validarFecha(fechaFin);

                    // Validacion fechas ingreso y salida.
                    if (fechaInicio.after(fechaFin)) {
                        throw new Exception("La fecha de inicio debe ser anterior a la fecha de fin.");
                    }

                    // Si no hay habitaciones disponibles en las fechas.
                    Habitacion habitacion = hotel.obtenerHabitacionDisponible(tipo, fechaInicio, fechaFin);
                    if (habitacion == null) {
                        throw new Exception("No hay habitaciones " + tipo.getNombre() + " disponibles para las fechas seleccionadas." + "\n");
                    }
                    fechasValidas = true;

                    // Mostrar habitación asignada si se encontraron fechas disponibles.
                    System.out.println("\n" + "Habitación asignada: Número " + habitacion.getNumero() + " - Tipo " + habitacion.getTipo().getNombre());

                    // Solicitar datos del cliente.
                    System.out.print("Nombre del cliente: ");
                    String nombreCliente = scanner.nextLine();

                    System.out.print("DNI del cliente: ");
                    String dniCliente = scanner.nextLine();

                    // Realizar la reserva.
                    Reserva reserva = hotel.reservarHabitacion(new Cliente(nombreCliente, dniCliente), tipo, fechaInicio, fechaFin);
                    System.out.println("\n" + "Reserva realizada correctamente:");
                    System.out.println(reserva); // Se llama al método toString() de Reserva para mostrar los datos.

                    // Si no estan disponibles las fechas ingresadas, sumar intentos fallidos de fechas y falla la reserva.
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    intentosFechas++;
                }
            }

            // Si después de 3 intentos no se encuentran fechas disponibles, sugiere cambiar el tipo de habitación.
            if (!fechasValidas) {
                System.out.println("No se encontraron fechas disponibles después de 3 intentos.");
                System.out.print("¿Desea cambiar el tipo de habitación? (si/no): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("si")) {
                    intentosFechas = 0;

                } else {
                    System.out.println("Reserva no realizada. Fechas no disponibles." + "\n");
                    break;
                }
            }
        }

        // Mostrar todas las reservas al terminar.
        System.out.println("\n" + "Reservas realizadas con éxito en esta sesión:");
        hotel.mostrarReservas();
        System.out.println("\n");
    }

    // Validaciones de fechas ingresadas.

    // Método para validar el formato de las fechas ingresadas por consola.
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseFecha (String fechaStr) {
        try {
            return dateFormat.parse(fechaStr);
        } catch (ParseException e) {
            System.err.println("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
            return null;
        }
    }

    private static void validarFecha(Date fecha) throws Exception {
        Date hoy = new Date();
        if (fecha.before(hoy)) {
            throw new Exception("La fecha ingresada es pasada. Por favor, ingrese una fecha futura.");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Los meses en Calendar son 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (year < 2024 || year > 2030) {
            throw new Exception("La fecha ingresada no es válida.");
        }

        if (month < 1 || month > 12) {
            throw new Exception("La fecha ingresada no es válida.");
        }

        if (day < 1 || day > 31) {
            throw new Exception("La fecha ingresada no es válida.");
        }
    }
}