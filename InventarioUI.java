import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;

public class InventarioUI extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private ProductoDAO dao = new ProductoDAO();

    public InventarioUI() {

        setTitle("Sistema de Inventario");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // TABLA
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // BOTONES
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnExportar = new JButton("Exportar CSV");

        panel.add(btnAgregar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);
        panel.add(btnExportar);

        add(panel, BorderLayout.SOUTH);

        // EVENTOS
        btnAgregar.addActionListener(e -> agregar());
        btnEliminar.addActionListener(e -> eliminar());
        btnActualizar.addActionListener(e -> cargarTabla());
        btnExportar.addActionListener(e -> exportar());

        cargarTabla();

        setVisible(true);

        // HILOS
        new HiloActualizarStock().start();
        new HiloSimularVentas().start();
    }

    private void agregar() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));

            dao.insertar(new Producto(nombre, precio, stock));
            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en datos");
        }
    }

    private void eliminar() {
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

    private void exportar() {
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
