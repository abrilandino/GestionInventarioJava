import java.io.Serializable;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String codigo;
    private final String nombre;
    private final int cantidad;
    private final double precio;

    public Producto(String codigo, String nombre, int cantidad, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return String.format("Código: %s | Nombre: %s | Cantidad: %d | Precio: %.2f",
                codigo, nombre, cantidad, precio);
    }
}

