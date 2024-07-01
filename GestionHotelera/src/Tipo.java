public class Tipo {
    private String nombre;
    private float precio;

    public Tipo(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }
}

// Clase para identificar los tipos de habitaciones (simple, doble o familiar)
