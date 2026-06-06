# TecnoStore POS

Sistema de consola en Java para la gestión de ventas, inventario y clientes de una tienda de celulares.

## Descripción del proyecto

TecnoStore POS es un sistema de punto de venta desarrollado en Java que permite gestionar el catálogo de celulares, registrar clientes, procesar ventas y generar reportes. Aplica principios de Programación Orientada a Objetos, patrones de diseño y persistencia con MySQL mediante JDBC.

## Estructura de clases

```
src/
└── com/tecnostore/pos/
    ├── Main.java                         # Menú principal de consola
    ├── modelo/
    │   ├── Producto.java                 # Clase abstracta base
    │   ├── Celular.java                  # Extiende Producto
    │   ├── Cliente.java                  # Entidad cliente
    │   ├── Venta.java                    # Entidad venta
    │   ├── ItemVenta.java                # Detalle de venta
    │   ├── CategoriaGama.java            # Enum: ALTA, MEDIA, BAJA
    │   └── SistemaOperativo.java         # Enum: ANDROID, IOS, HARMONYOS
    ├── persistencia/
    │   ├── ConexionDB.java               # Singleton con Double-Checked Locking
    │   ├── ICelularDAO.java              # Interfaz DAO celulares
    │   ├── IClienteDAO.java              # Interfaz DAO clientes
    │   ├── IVentaDAO.java                # Interfaz DAO ventas
    │   ├── CelularDAO.java               # CRUD celulares con JDBC
    │   ├── ClienteDAO.java               # CRUD clientes con JDBC
    │   └── VentaDAO.java                 # Transacciones ACID de ventas
    ├── servicio/
    │   ├── GestorCelulares.java          # Reglas de negocio celulares
    │   ├── GestorClientes.java           # Reglas de negocio clientes
    │   └── GestorVentas.java             # Reglas de negocio ventas
    ├── patron/
    │   ├── FactoryCelular.java           # Factory: asigna gama por precio
    │   ├── EstrategiaDescuento.java      # Interfaz Strategy
    │   ├── SinDescuento.java             # Estrategia sin descuento
    │   ├── DescuentoGamaMedia.java       # Descuento 10% gama media
    │   ├── DescuentoGamaBaja.java        # Descuento 5% gama baja
    │   └── StrategyDescuento.java        # Contexto Strategy
    └── util/
        ├── Validador.java                # Validaciones reutilizables
        ├── ReporteUtils.java             # Reportes con Stream API
        └── ArchivoUtils.java             # Generación de reporte_ventas.txt
```

## Patrones de diseño implementados

- **Singleton**: `ConexionDB` — instancia única de conexión con Double-Checked Locking
- **Factory**: `FactoryCelular` — crea celulares y asigna gama automáticamente según precio
- **Strategy**: `StrategyDescuento` — aplica descuentos según gama del celular

## Ejemplo de ejecución

```
Bienvenido a TecnoStore POS

--- Menu Principal ---
1. Gestion de Celulares
2. Gestion de Clientes
3. Registrar Venta
4. Reportes
0. Salir
Seleccione una opcion: 1

--- Gestion de Celulares ---
1. Registrar celular
2. Listar celulares
3. Actualizar celular
4. Eliminar celular
Seleccione una opcion: 1
Marca: Samsung
Modelo: Galaxy S24
Precio: 3500000
Stock: 10
Sistema operativo (ANDROID/IOS/HARMONYOS): android
Celular registrado con ID: 1 | Gama asignada: ALTA
```

## Indicaciones para conexión MySQL

1. Crear el archivo `src/config.properties` (no incluido en el repositorio por seguridad):

```properties
db.url=jdbc:mysql://HOST:PUERTO/tecnostore_db?ssl-mode=REQUIRED
db.user=USUARIO
db.password=CONTRASENA
```

2. Ejecutar el script `tecnostore_db.sql` en tu servidor MySQL para crear las tablas.

3. El driver MySQL Connector/J 9.7.0 está incluido en `libs/`.

## Tecnologías utilizadas

- Java 17
- MySQL 8.0 (Aiven Cloud)
- JDBC — MySQL Connector/J 9.7.0
- Apache NetBeans 18
- Git / GitHub