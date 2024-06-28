public class Habitacion {
    private int numero;
    private boolean disponible;
    private Tipo tipo;
    private static int contadorSimple = 100;
    private static int contadorDoble = 200;
    private static int contadorFamiliar = 300;

    public Habitacion(Tipo tipo) {
        this.numero = asignarNumero(tipo);
        this.disponible = true;
        this.tipo = tipo;
    }

    // Asignar número a las habitaciones (simple: desde 100, doble: desde 200, familiar: desde 300)
    private int asignarNumero(Tipo tipo) {
        switch (tipo.getNombre()) {
            case "simple":
                return contadorSimple++;
            case "doble":
                return contadorDoble++;
            case "familiar":
                return contadorFamiliar++;
            default:
                throw new IllegalArgumentException("Tipo de habitación desconocido: " + tipo.getNombre());
        }
    }

    public int getNumero() {
        return numero;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
