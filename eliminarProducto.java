private void eliminarProducto() {
    String codigo = tfCodigo.getText().trim();

    // Validar campo vacío
    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el código a eliminar.");
        return;
    }

    boolean ok = repo.deleteByCodigo(codigo);
    if (ok) {
        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        limpiarCampos();
    } else {
        JOptionPane.showMessageDialog(this, "Producto no encontrado.");
    }
}
