private void guardarProducto() {
    String codigo = tfCodigo.getText().trim();
    String nombre = tfNombre.getText().trim();

    // Validar campos vacíos
    if (codigo.isEmpty() || nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Código y Nombre son obligatorios.");
        return;
    }

    // Validar tipo numérico para cantidad
    int cantidad;
    try {
        cantidad = Integer.parseInt(tfCantidad.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida (entero).");
        return;
    }

    // Validar tipo numérico para precio
    double precio;
    try {
        precio = Double.parseDouble(tfPrecio.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Ingrese un precio válido (número).");
        return;
    }

    // Si todo está correcto, guardar el producto
    Producto p = new Producto(codigo, nombre, cantidad, precio);
    repo.save(p);
    JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
    limpiarCampos();
}
