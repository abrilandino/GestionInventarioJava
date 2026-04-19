import java.util.ArrayList;

public class HiloActualizarStock extends Thread {

    private ProductoDAO dao;

    public HiloActualizarStock(ProductoDAO dao) {
        this.dao = dao;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ArrayList<Producto> productos = dao.obtenerProductos();

                for (Producto p : productos) {
                    if (p.getCantidad() < 5) {
                        System.out.println("Stock bajo detectado en: " + p.getNombre());
                        dao.aumentarStock(p.getId(), 10);
                    }
                }

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                System.out.println("HiloActualizarStock interrumpido: " + e.getMessage());
            }
        }
    }
}
