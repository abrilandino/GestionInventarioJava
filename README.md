# GestionInventarioJava
# Este es un codigo para gestion de inventario con frontend y backend
# Proyecto de trabajo grupo 2

# Sistema de Gestión de Inventario (Java + MySQL)

Aplicación de escritorio en **Java** para administrar el inventario de productos: permite **crear, consultar, actualizar y eliminar** productos (CRUD) guardados en una base de datos **MySQL**, con una interfaz gráfica.

Proyecto en equipo (Grupo 2) de la carrera de Ingeniería en Infotecnología.

<!--
## Captura de pantalla

Cuando tengas la captura, súbela a la carpeta docs/ y descomenta este bloque:

![Pantalla principal](docs/pantalla-principal.png)
-->

## Funcionalidades

- **CRUD de productos:** agregar, ver, editar y eliminar productos del inventario.
- **Interfaz gráfica** para manejar el inventario sin usar la consola.
- **Base de datos MySQL** donde se guardan los productos.
- **Uso de hilos (threads)** para actualizar el stock y para simular ventas (`HiloActualizarStock` y `HiloSimularVentas`).

## Tecnologías

| Área | Tecnología |
| --- | --- |
| Lenguaje | Java |
| Base de datos | MySQL (con XAMPP) |
| Conexión a la BD | MySQL Connector/J 9.7.0 (incluido en el repo) |
| Control de versiones | Git y GitHub |

## Estructura del proyecto

| Archivo | Qué hace |
| --- | --- |
| `Main.java` | Punto de entrada del programa |
| `InventarioUI.java` | Interfaz gráfica |
| `Producto.java` | Clase que representa un producto |
| `ProductoDAO.java` | Operaciones CRUD contra la base de datos |
| `ConexionBD.java` | Conexión con MySQL |
| `HiloActualizarStock.java` | Hilo que actualiza el stock |
| `HiloSimularVentas.java` | Hilo que simula ventas |
| `BaseDeDatos.sql` | Script para crear la base de datos y las tablas |

## Cómo ejecutarlo

### Requisitos

- [Java JDK](https://www.oracle.com/java/technologies/downloads/) 11 o superior
- [XAMPP](https://sourceforge.net/projects/xampp/files/XAMPP%20Windows/8.2.12/xampp-windows-x64-8.2.12-0-VS16-installer.exe/download) (para MySQL)

### Pasos

1. **Clona el repositorio**
```bash
   git clone https://github.com/abrilandino/GestionInventarioJava.git
   cd GestionInventarioJava
```

2. **Inicia MySQL**: abre XAMPP y presiona *Start* en MySQL.

3. **Crea la base de datos**: entra a `http://localhost/phpmyadmin`, ve a *Importar* y selecciona el archivo `BaseDeDatos.sql`.

4. **Revisa la conexión** en `ConexionBD.java`: el usuario y la contraseña deben coincidir con tu MySQL local (en XAMPP, por defecto el usuario es `root` y la contraseña está vacía).

5. **Compila y ejecuta** (en Windows):
```bash
   javac -cp ".;mysql-connector-j-9.7.0.jar" *.java
   java -cp ".;mysql-connector-j-9.7.0.jar" Main
```

   > En Linux o macOS, cambia el `;` por `:` en el classpath:
   > `javac -cp ".:mysql-connector-j-9.7.0.jar" *.java`

### Ejecutar desde un IDE (NetBeans, IntelliJ o Eclipse)

1. Abre o importa la carpeta del proyecto en tu IDE.
2. Agrega `mysql-connector-j-9.7.0.jar` a las librerías del proyecto (*Add JAR/Folder* o *Libraries*).
3. Ejecuta la clase `Main`.

## Mi participación

Proyecto en equipo. Mi parte fue:

- Desarrollar las operaciones **CRUD** conectadas a la base de datos MySQL.
- Diseñar la **interfaz gráfica** de la aplicación.

## Autores

- **Abril Andino Reyes** – [@abrilandino](https://github.com/abrilandino)
Descargar XAMPP:
https://sourceforge.net/projects/xampp/files/XAMPP%20Windows/8.2.12/xampp-windows-x64-8.2.12-0-VS16-installer.exe/download
