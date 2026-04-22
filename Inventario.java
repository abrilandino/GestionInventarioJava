import java.util.ArrayList;

public class Inventario {
    private final ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    // Agregar producto con validación de duplicados
    public boolean agregarProducto(Producto p) {
        if (p == null || p.getCodigo() == null || p.getCodigo().trim().isEmpty()) {
            System.out.println("El producto debe tener un código válido.");
            return false;
        }
        for (Producto existente : productos) {
            if (existente.getCodigo().equalsIgnoreCase(p.getCodigo())) {
                System.out.println("Ya existe un producto con el mismo código.");
                return false;
            }
        }
        productos.add(p);
        return true;
    }

    public boolean eliminarProducto(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.out.println("Debe ingresar un código válido para eliminar.");
            return false;
        }
        return productos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }

    public Producto buscarProducto(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.out.println("Debe ingresar un código válido para buscar.");
            return null;
        }
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("Inventario vacío.");
        } else {
            for (Producto p : productos) {
                System.out.println(p);
            }
        }
    }
}
