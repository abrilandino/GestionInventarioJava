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
        setTitle("sistema de inventario");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // panel de entrada con color azul claro
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 10, 10));
        panelForm.setBackground(new Color(220, 235, 250));
        panelForm.setBorder(BorderFactory.createTitledBorder("gestion de productos"));

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        panelForm.add(new JLabel("nombre:"));
        panelForm.add(new JLabel("precio:"));
        panelForm.add(new JLabel("stock:"));
        panelForm.add(new JLabel(""));

        panelForm.add(txtNombre);
        panelForm.add(txtPrecio);
        panelForm.add(txtStock);

        JButton btnAgregar = new JButton("agregar");
        panelForm.add(btnAgregar);
        add(panelForm, BorderLayout.NORTH);

        // tabla con fondo gris claro en el scrollpane
        modelo = new DefaultTableModel(new String[]{"ID", "producto", "precio", "stock"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(240, 240, 240));
        add(scroll, BorderLayout.CENTER);

        // panel de botones inferior
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("eliminar");
        JButton btnEditar = new JButton("editar");
        JButton btnActualizar = new JButton("refrescar");
        JButton btnExportar = new JButton("exportar csv");

        panelBotones.add(btnEliminar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);
        add(panelBotones, BorderLayout.SOUTH);

        // asignacion de eventos a los botones
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
        
        // temporizador para refrescar la tabla cada 3 segundos
        new Timer(3000, e -> cargarTabla()).start();
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
            JOptionPane.showMessageDialog(this, "error en los datos");
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
        int filaSeleccionada = tabla.getSelectedRow();
        modelo.setRowCount(0);
        for (Producto p : dao.listar()) {
            modelo.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio(), p.getStock()});
        }
        if (filaSeleccionada != -1 && filaSeleccionada < modelo.getRowCount()) {
            tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
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
            JOptionPane.showMessageDialog(this, "exportado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al exportar");
        }
    }
}
