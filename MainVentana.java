public class MainVentana {

    public static void main(String[] args) {

        ProductoDAO dao = new ProductoDAO();

        // Insertar productos de prueba
        dao.insertar("Arroz", 25.50, 3);
        dao.insertar("Frijoles", 18.75, 8);
        dao.insertar("Azucar", 20.00, 2);

        System.out.println("=== INVENTARIO INICIAL ===");
        dao.listar();

        // Crear hilos
        HiloActualizarStock hiloStock = new HiloActualizarStock(dao);
        HiloSimularVentas hiloVentas = new HiloSimularVentas(dao);

        // Iniciar hilos
        hiloStock.start();
        hiloVentas.start();
    }
}
