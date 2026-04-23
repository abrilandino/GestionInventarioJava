import java.util.Random;

/**
 * Hilo que simula ventas (reduce stock)
 */
public class HiloSimularVentas extends Thread {

    ProductoDAO dao = new ProductoDAO();
    Random rand = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(8000);

                for (Producto p : dao.listar()) {
                    int venta = rand.nextInt(3); // ventas aleatorias

                    int nuevoStock = Math.max(0, p.getStock() - venta);
                    dao.actualizarStock(p.getId(), nuevoStock);
                }

                System.out.println("Ventas simuladas");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
