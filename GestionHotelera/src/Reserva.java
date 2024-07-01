import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reserva {
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaInicio;
    private Date fechaFin;

    public Reserva(Cliente cliente, Habitacion habitacion, Date fechaInicio, Date fechaFin) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    // Calcular el precio de una reserva: Precio de la habitacion * dias de estadía.
    public double calcularPrecio() {
        long diffInMillies = Math.abs(fechaFin.getTime() - fechaInicio.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff * habitacion.getTipo().getPrecio();
    }

    // Mostrar datos de la reserva realizada correctamente por consola.
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Cliente: " + cliente.getNombre() + " | " + cliente.getDni() + "\n" +
                "Número de Habitación: " + habitacion.getNumero() + "\n" +
                "Tipo de Habitación: " + habitacion.getTipo().getNombre() + "\n" +
                "Fecha de check in: " + dateFormat.format(fechaInicio) + "\n" +
                "Fecha de check out: " + dateFormat.format(fechaFin) + "\n" +
                "Precio total: $" + calcularPrecio() + "\n";
    }
}

//Clase para realizar las reservas del hotel.