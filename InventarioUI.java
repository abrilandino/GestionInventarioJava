import java.awt.*;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventarioUI extends JFrame {

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final ProductoDAO dao = new ProductoDAO();
    private JTextField txtNombre, txtPrecio, txtStock;

    public InventarioUI() {
        setTitle("Sistema de Inventario");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // panel superior de color azul claro para agregar
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 10, 10));
        panelForm.setBackground(new Color(210, 230, 250));
        panelForm.setBorder(BorderFactory.createTitledBorder("Gestion de Productos"));

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        panelForm.add(new JLabel("Nombre del Producto:"));
        panelForm.add(new JLabel("Precio:"));
        panelForm.add(new JLabel("Stock:"));
        panelForm.add(new JLabel(""));

        panelForm.add(txtNombre);
        panelForm.add(txtPrecio);
        panelForm.add(txtStock);

        JButton btnAgregar = new JButton("Agregar");
        panelForm.add(btnAgregar);
        add(panelForm, BorderLayout.NORTH);

        // area de inventario con fondo gris claro
        modelo = new DefaultTableModel(new String[]{"ID", "Producto", "Precio", "Stock"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(235, 235, 235));
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnEditar = new JButton("Editar");
        JButton btnActualizar = new JButton("Refrescar");
        JButton btnExportar = new JButton("Exportar CSV");

        panelBotones.add(btnEliminar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregar());
        btnEliminar.addActionListener(e -> eliminar());
        btnEditar.addActionListener(e -> editar());
        btnActualizar.addActionListener(e -> cargarTabla());
        btnExportar.addActionListener(e -> exportar());

        tabla.getSelectionModel().addListSelectionListener(e -> llenarCampos());

        cargarTabla();
        setVisible(true);

        new HiloActualizarStock().start();
        new HiloSimularVentas().start();

        // refresca la tabla cada 2 segundos para ver cambios de los hilos
        new Timer(2000, e -> cargarTabla()).start();
    }

    private void agregar() {
        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());
            if (!nombre.isEmpty()) {
                dao.insertar(new Producto(nombre, precio, stock));
                limpiar();
                cargarTabla();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            dao.eliminar((int) modelo.getValueAt(fila, 0));
            cargarTabla();
        }
    }

    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            dao.actualizar(id, txtNombre.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
            limpiar();
            cargarTabla();
        }
    }

    private void cargarTabla() {
        int filaActual = tabla.getSelectedRow();
        modelo.setRowCount(0);
        for (Producto p : dao.listar()) {
            modelo.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio(), p.getStock()});
        }
        if (filaActual != -1 && filaActual < modelo.getRowCount()) {
            tabla.setRowSelectionInterval(filaActual, filaActual);
        }
    }

    private void llenarCampos() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtPrecio.setText(modelo.getValueAt(fila, 2).toString());
            txtStock.setText(modelo.getValueAt(fila, 3).toString());
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }

    private void exportar() {
        try (FileWriter fw = new FileWriter("inventario.csv")) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                fw.write(modelo.getValueAt(i, 0) + "," + modelo.getValueAt(i, 1) + "," + modelo.getValueAt(i, 2) + "," + modelo.getValueAt(i, 3) + "\n");
            }
            JOptionPane.showMessageDialog(this, "Exportacion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar archivo");
        }
    }
}
}
