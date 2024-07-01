import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Hotel {
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    public Hotel() {
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // Agregar habitacion al hotel.
    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    // Valida que las fechas que se ingresen para una reserva 
    // esten disponibles para alguna habitacion del tipo seleccionado.
    public boolean esFechaDisponible(Habitacion habitacion, Date fechaInicio, Date fechaFin) {
        for (Reserva reserva : reservas) {
            if (reserva.getHabitacion().getNumero() == habitacion.getNumero()) {
                if (fechaInicio.before(reserva.getFechaFin()) && fechaFin.after(reserva.getFechaInicio())) {
                    return false;
                }
            }
        }
        return true;
    }

    // Verificar que una habitacion este disponible en determinadas fechas.
    public Habitacion obtenerHabitacionDisponible(Tipo tipo, Date fechaInicio, Date fechaFin) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equals(tipo) && esFechaDisponible(habitacion, fechaInicio, fechaFin)) {
                return habitacion;
            }
        }
        return null;
    }

    // Realizar una reserva.
    public Reserva reservarHabitacion(Cliente cliente, Tipo tipo, Date fechaInicio, Date fechaFin) throws Exception {
        Habitacion habitacion = obtenerHabitacionDisponible(tipo, fechaInicio, fechaFin);
        if (habitacion != null) {
            Reserva reserva = new Reserva(cliente, habitacion, fechaInicio, fechaFin);
            reservas.add(reserva);
            return reserva;
        } else {
            throw new Exception("No hay habitaciones disponibles de tipo " + tipo.getNombre() + " para las fechas seleccionadas.");
        }
    }

    // Mostrar todas las reservas almacenadas.
    public void mostrarReservas() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (Reserva reserva : reservas) {
            System.out.println("Reserva: " + reserva.getCliente().getNombre() + " en la habitación " + reserva.getHabitacion().getNumero() + " desde " + dateFormat.format(reserva.getFechaInicio()) + " hasta " + dateFormat.format(reserva.getFechaFin()) + " por $" + reserva.calcularPrecio());
        }
    }
}

// Esta clase es la base del programa. El Hotel realiza y guarda las reservas, 
// crea las habitaciones, registra a los clientes y gestiona las fechas.