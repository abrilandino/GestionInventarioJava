import java.util.Scanner;

public class MainProvisional {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Inventario inventario = new Inventario();
            int opcion = 0;

            do {
                System.out.println("\n===== PRUEBA INVENTARIO =====");
                System.out.println("1. Agregar producto");
                System.out.println("2. Eliminar producto");
                System.out.println("3. Buscar producto");
                System.out.println("4. Listar productos");
                System.out.println("0. Salir");
                System.out.print("Opción: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Ingrese un número válido.");
                    sc.nextLine();
                    continue;
                }

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Código: ");
                        String codigo = sc.nextLine().trim();
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine().trim();

                        if (codigo.isEmpty() || nombre.isEmpty()) {
                            System.out.println("Código y Nombre son obligatorios.");
                            break;
                        }

                        System.out.print("Cantidad: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Cantidad debe ser un número entero.");
                            sc.nextLine();
                            break;
                        }
                        int cantidad = sc.nextInt();

                        System.out.print("Precio: ");
                        if (!sc.hasNextDouble()) {
                            System.out.println("Precio debe ser un número decimal.");
                            sc.nextLine();
                            break;
                        }
                        double precio = sc.nextDouble();
                        sc.nextLine();

                        if (cantidad < 0 || precio < 0) {
                            System.out.println("Cantidad y Precio no pueden ser negativos.");
                            break;
                        }

                        Producto nuevo = new Producto(codigo, nombre, cantidad, precio);
                        if (inventario.agregarProducto(nuevo)) {
                            System.out.println("Producto agregado correctamente.");
                        }
                    }

                    case 2 -> {
                        System.out.print("Código a eliminar: ");
                        String eliminar = sc.nextLine().trim();
                        if (eliminar.isEmpty()) {
                            System.out.println("Debe ingresar un código.");
                            break;
                        }
                        if (inventario.eliminarProducto(eliminar)) {
                            System.out.println("Producto eliminado.");
                        } else {
                            System.out.println("Producto no encontrado.");
                        }
                    }

                    case 3 -> {
                        System.out.print("Código a buscar: ");
                        String buscar = sc.nextLine().trim();
                        Producto encontrado = inventario.buscarProducto(buscar);
                        if (encontrado != null) {
                            System.out.println(encontrado);
                        } else {
                            System.out.println("No existe.");
                        }
                    }

                    case 4 -> inventario.listarProductos();

                    case 0 -> System.out.println("Saliendo...");

                    default -> System.out.println("Opción inválida.");
                }
            } while (opcion != 0);
        }
    }
}
