package hilos;

import modelo.Producto;
import modelo.ProductoDAO;
import java.util.ArrayList;
import java.util.Random;

public class HiloSimularVentas extends Thread {

    private ProductoDAO dao;
    private Random random = new Random();

    public HiloSimularVentas(ProductoDAO dao) {
        this.dao = dao;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ArrayList<Producto> productos = dao.obtenerProductos();

                if (!productos.isEmpty()) {
                    Producto productoSeleccionado = productos.get(random.nextInt(productos.size()));

                    int cantidadVenta = random.nextInt(3) + 1; // vende entre 1 y 3 unidades

                    System.out.println("Simulando venta de " + cantidadVenta +
                            " unidad(es) de: " + productoSeleccionado.getNombre());

                    dao.disminuirStock(productoSeleccionado.getId(), cantidadVenta);
                }

                Thread.sleep(3000); // espera 3 segundos

            } catch (InterruptedException e) {
                System.out.println("HiloSimularVentas interrumpido: " + e.getMessage());
            }
        }
    }
}
