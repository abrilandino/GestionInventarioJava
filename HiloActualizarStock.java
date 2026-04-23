/**
 * Hilo que aumenta el stock automáticamente
 */
public class HiloActualizarStock extends Thread {

    ProductoDAO dao = new ProductoDAO();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000); // cada 10 segundos

                for (Producto p : dao.listar()) {
                    dao.actualizarStock(p.getId(), p.getStock() + 1);
                }

                System.out.println("Stock actualizado automáticamente");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
