import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;

/**
 * Interfaz principal (TU PARTE)
 */
public class InventarioUI extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private ProductoDAO dao = new ProductoDAO();

    public InventarioUI() {
        setTitle("Sistema de Inventario - Abril");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // TABLA
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // PANEL BOTONES
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar Tabla");
        JButton btnExportar = new JButton("Exportar CSV");

        panel.add(btnAgregar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);
        panel.add(btnExportar);

        add(panel, BorderLayout.SOUTH);

        // EVENTOS
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> cargarTabla());
        btnExportar.addActionListener(e -> exportarCSV());

        cargarTabla();
        setVisible(true);

        // HILOS
        new HiloActualizarStock().start();
        new HiloSimularVentas().start();
    }

    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog("Nombre:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));

        dao.insertar(new Producto(nombre, precio, stock));
        cargarTabla();
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            dao.eliminar(id);
            cargarTabla();
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Producto p : dao.listar()) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            });
        }
    }

    private void exportarCSV() {
        try {
            FileWriter fw = new FileWriter("inventario.csv");

            for (int i = 0; i < modelo.getRowCount(); i++) {
                fw.write(
                        modelo.getValueAt(i, 0) + "," +
                        modelo.getValueAt(i, 1) + "," +
                        modelo.getValueAt(i, 2) + "," +
                        modelo.getValueAt(i, 3) + "\n"
                );
            }

            fw.close();
            JOptionPane.showMessageDialog(this, "Exportado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
