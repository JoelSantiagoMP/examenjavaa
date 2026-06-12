# TecnoStore POS

Sistema de consola en Java para la gestión de ventas, inventario, clientes y créditos de una tienda de celulares.

## Descripción del proyecto

TecnoStore POS es un sistema de punto de venta desarrollado en Java que permite gestionar el catálogo de celulares, registrar clientes, procesar ventas, gestionar créditos y deudas pendientes, y generar reportes. Aplica principios de Programación Orientada a Objetos, patrones de diseño y persistencia con MySQL mediante JDBC.

Al registrar una venta, el sistema pregunta el monto pagado por el cliente y genera automáticamente un crédito a favor o una deuda pendiente según la diferencia, manteniéndolo registrado en base de datos.

## Estructura de clases
src/
└── com/tecnostore/pos/
├── Main.java                         # Menú principal de consola
├── modelo/
│   ├── Producto.java                 # Clase abstracta base
│   ├── Celular.java                  # Extiende Producto
│   ├── Cliente.java                  # Entidad cliente
│   ├── Venta.java                    # Entidad venta
│   ├── ItemVenta.java                # Detalle de venta
│   ├── Credito.java                  # Entidad crédito/deuda generada por venta
│   ├── CategoriaGama.java            # Enum: ALTA, MEDIA, BAJA
│   ├── SistemaOperativo.java         # Enum: ANDROID, IOS, HARMONYOS
│   └── TipoCredito.java              # Enum: DEUDA, CREDITO_A_FAVOR
├── persistencia/
│   ├── ConexionDB.java               # Singleton con Double-Checked Locking
│   ├── ICelularDAO.java              # Interfaz DAO celulares
│   ├── IClienteDAO.java              # Interfaz DAO clientes
│   ├── IVentaDAO.java                # Interfaz DAO ventas
│   ├── ICreditoDAO.java              # Interfaz DAO créditos
│   ├── CelularDAO.java               # CRUD celulares con JDBC
│   ├── ClienteDAO.java               # CRUD clientes con JDBC
│   ├── VentaDAO.java                 # Transacciones ACID de ventas
│   └── CreditoDAO.java               # Inserción y consulta de créditos con JDBC
├── servicio/
│   ├── GestorCelulares.java          # Reglas de negocio celulares
│   ├── GestorClientes.java           # Reglas de negocio clientes
│   ├── GestorVentas.java             # Reglas de negocio ventas
│   ├── GestorCreditos.java           # Reglas de negocio créditos
│   └── ReporteService.java           # Singleton: genera reporte_global.txt y procesa pagos
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
├── ReporteGlobalUtils.java       # Cálculos globales con Stream API
└── ArchivoUtils.java             # Generación de reporte_ventas.txt

## Patrones de diseño implementados

- **Singleton**: `ConexionDB` — instancia única de conexión con Double-Checked Locking
- **Singleton**: `ReporteService` — instancia única para generación de reportes globales y procesamiento de pagos
- **Factory**: `FactoryCelular` — crea celulares y asigna gama automáticamente según precio
- **Strategy**: `StrategyDescuento` — aplica descuentos según gama del celular

## Principios SOLID aplicados

- **S** (Single Responsibility): cada clase tiene una única responsabilidad. `ReporteGlobalUtils` solo calcula, `ReporteService` solo orquesta y escribe.
- **O** (Open/Closed): se pueden agregar nuevas secciones al reporte sin modificar las existentes.
- **L** (Liskov Substitution): `Credito` usa composición con `Cliente` en lugar de herencia, ya que un crédito *tiene* un cliente, no *es* un cliente.
- **D** (Dependency Inversion): los gestores dependen de interfaces (`ICreditoDAO`, `ICelularDAO`) y no de implementaciones concretas.

## Lógica de créditos y deudas

Al registrar una venta el sistema calcula automáticamente la diferencia entre el total y el monto pagado:

| Escenario | Resultado |
|---|---|
| Pago exacto | Sin registro de crédito |
| Pago menor al total | Se genera una `DEUDA` |
| Pago mayor al total | Se genera un `CREDITO_A_FAVOR` |

El saldo se puede actualizar posteriormente cuando el cliente salda su deuda o la tienda devuelve el excedente. Cuando el saldo llega a cero se considera saldado.

## Reporte global

El archivo `reporte_global.txt` se genera desde el menú de reportes e incluye:

1. Total facturado y unidades vendidas
2. Celulares vendidos agrupados por marca y modelo
3. Clientes con deudas pendientes
4. Clientes con crédito a favor
5. Stock actual con alertas para productos por debajo de 5 unidades

## Ejemplo de ejecución
Bienvenido a TecnoStore POS
--- Menu Principal ---

Gestion de Celulares
Gestion de Clientes
Registrar Venta
Reportes
Salir
Seleccione una opcion: 3

ID del cliente: 1
ID del celular: 2
Descuento aplicado: Sin descuento
Precio final: $4400000
Cantidad: 1
Agregar otro celular? (s/n): n
Venta registrada. Total: $4400000.00
Monto pagado por el cliente: $4000000
Queda pendiente: $400000.00

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

## Autor(es)

- Jorge Gomez
- Joel Martinez