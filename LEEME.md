# Evaluación T2 actualizada: órdenes de compra

Adaptación del proyecto original y de los recursos del profesor Ernesto Arbulú al PDF del 10/10/2026. Conserva Java 21, Spring Boot 4.1.1, Lombok, JPA, MySQL, Thymeleaf, Bootstrap y el diseño de los tres HTML adjuntos. No cambia las versiones del proyecto original.

## 1. Base utilizada y nombres

Se utilizó el proyecto incluido en EXAMEN.zip, no un proyecto desde cero. El archivo ya identifica Hermoza como apellido y Jose como nombre. Por el orden exigido en el PDF, el proyecto se llama `LP2_T2_Hermoza_Jose`; las clases son `RubroHermozaJose`, `ProveedorHermozaJose` y `OrdenCompraHermozaJose`. Los HTML conservan los nombres que ya tenías: `listadoHermozaJose.html`, `nuevoHermozaJose.html`, `edicionHermozaJose.html`.

Se conserva el paquete original `com.examenT2.LP2_T2_Jose_Hermoza`, la clase principal `Lp2T2JoseHermozaApplication`, el test de contexto, ResultadoResponse, Alert y las configuraciones de Java 21 y MySQL. El orden del paquete no está prescrito en el PDF: no es necesario cambiarlo para que coincida con la carpeta.

Se renombraron las tres entidades para cumplir el sufijo solicitado, manteniendo sus campos, anotaciones y relaciones. Se corrigió OrdenCompraRepository: debe ser una interfaz que extienda JpaRepository de la entidad; no una clase con una interfaz interna que se referencia a sí misma. Se completaron los dos repositorios vacíos, el controller vacío, los servicios faltantes y las expresiones Thymeleaf de los HTML.

## 2. Preparar la base

Ejecuta `database/database_t2.sql` en MySQL Workbench. Es el SQL adjunto del profesor, con sus mismas tablas y registros: 5 rubros, 10 proveedores y 18 órdenes. El script original recrea `database_t2`; úsalo en tu base de práctica.

La estructura ahora es:

```text
tbl_orden_compra.id_proveedor -> tbl_proveedor.id_proveedor
tbl_proveedor.id_rubro -> tbl_rubro.id_rubro
```

Para la temática anime solicitada anteriormente, se incluye `database/database_anime_opcional.sql`. Tiene la misma estructura y cantidades, con proveedores y rubros ficticios. Si eliges esta variante, ejecuta ese script y cambia solamente el nombre de base en la URL de `application.properties` a `anime_compras_t2`. Para reproducir los datos de la evaluación utiliza el SQL principal.

## 3. Importar en Spring Tool Suite

1. Extrae el ZIP completo.
2. Selecciona la carpeta `LP2_T2_Hermoza_Jose` incluida en el ZIP.
3. Abre STS y entra a **File > Import > Maven > Existing Maven Projects**.
4. Selecciona la carpeta que contiene `pom.xml` y pulsa **Finish**.
5. Configura un JDK 21 en **Window > Preferences > Java > Installed JREs**.
6. Si hace falta, clic derecho en el proyecto > **Maven > Update Project**.
7. Configura Lombok en tu instalación de STS si las anotaciones aparecen con error. La dependencia Maven y el procesador de anotaciones ya están incluidos; el IDE también debe reconocer Lombok.

## 4. Revisar las entidades

Están en `src/main/java/com/examenT2/LP2_T2_Jose_Hermoza/model`:

| Clase | Tabla | Campos y relación |
| --- | --- | --- |
| RubroHermozaJose | tbl_rubro | idRubro autogenerado, denominacion |
| ProveedorHermozaJose | tbl_proveedor | idProveedor autogenerado, ruc, razonSocial, fechaRegistro, direccion, paginaWeb, rubro |
| OrdenCompraHermozaJose | tbl_orden_compra | nroOrden autogenerado, proveedor, monto, direccionEntrega, fechaEntrega, estado |

`@Entity` identifica una entidad JPA; `@Table` indica su tabla; `@Column` conserva el nombre SQL; `@Id` y `@GeneratedValue(IDENTITY)` usan AUTO_INCREMENT. `@ManyToOne` y `@JoinColumn` representan las dos llaves foráneas. `LocalDateTime` corresponde al DATETIME del proveedor; `LocalDate` corresponde al DATE de entrega. `BigDecimal` mantiene la precisión del DECIMAL(10,2).

Lombok conserva `@Getter`, `@Setter`, `@NoArgsConstructor` y `@AllArgsConstructor`. `@DynamicInsert`, `@ColumnDefault("'P'")` y el valor inicial `"P"` conservan el estado Pendiente. `getEstadoDescripcion()` muestra P=Pendiente, C=Completado, A=Anulado.

El comentario del SQL dice por error «A: Pendiente». Se siguen los valores permitidos, el default P y el combo del HTML: P=Pendiente. Las menciones a «inventarios» en algunos párrafos del PDF son inconsistentes con las entidades, el SQL y la imagen; se implementan órdenes de compra.

## 5. Repositorios, servicios y controller

Cada repositorio extiende `JpaRepository<Entidad, Integer>`. `findAllByOrderByNroOrdenDesc()` lista del número mayor al menor.

El PDF nuevo pide servicios con sus implementaciones: se crean interfaces `RubroService`, `ProveedorService` y `OrdenCompraService`, y sus clases `...ServiceImpl` en `service/impl`. Las implementaciones conservan `@Service`, `@RequiredArgsConstructor`, las dependencias `private final` y los métodos del ejemplo `getAll`, `getOne`, `create` y `update`. Se conservan `ResultadoResponse`, `Alert.sweetToast` y el flujo con `RedirectAttributes`.

`OrdenCompraController` usa `@Controller` y atiende:

| Ruta | Acción |
| --- | --- |
| GET /ordenCompra/listado | Muestra todas las órdenes descendentes |
| GET /ordenCompra/nuevo | Carga el combo de proveedores y una orden vacía |
| POST /ordenCompra/registrar | Registra y redirige al listado |
| GET /ordenCompra/edicion/{numero} | Carga los datos y el proveedor seleccionado |
| POST /ordenCompra/guardar | Actualiza el mismo registro y redirige |

Al registrar se fuerza el estado P: el HTML nuevo del profesor no contiene un combo de estado. La edición permite seleccionar P, C o A. Se validan proveedor existente, dirección, fecha, precisión del monto y estados permitidos. Una actualización con número inexistente se rechaza y no crea otra orden.

## 6. Revisar los HTML

En `src/main/resources/templates` están `listadoHermozaJose.html`, `nuevoHermozaJose.html` y `edicionHermozaJose.html` (ya personalizados con los datos de tu proyecto).

Se conserva el diseño original: contenedor, tarjeta, tabla, formularios, Bootstrap y botones. Solo se completan las expresiones Thymeleaf y las rutas, se agrega el número oculto en edición y se cambia su botón a **Guardar**, como pide el PDF.

El listado muestra número, fecha, dirección, `ruc + ' - ' + razonSocial`, rubro, página web, monto con `#numbers.formatCurrency` y descripción del estado. Los combos usan `th:each`, `th:value` y `th:field`: este último selecciona el proveedor guardado al editar.

Mensajes exactos:

- `Orden de compra Nro. X registrada.`
- `Orden de compra Nro. X actualizada.`

Se muestran con SweetAlert y también en la página, incluso si el navegador no carga el CDN.

## 7. Ajustar conexión y ejecutar

En `src/main/resources/application.properties` revisa:

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/database_t2?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=mysql
spring.jpa.hibernate.ddl-auto=validate
```

Coloca tu usuario y contraseña reales. La opción `validate` verifica las tablas sin sustituir el SQL del profesor. Clic derecho sobre el proyecto > **Run As > Spring Boot App**. Visita:

```text
http://localhost:8081/ordenCompra/listado
```

## 8. Probar según la rúbrica

1. Verifica 18 órdenes, del número 18 al 1, con todas las columnas.
2. Pulsa **Nuevo**: el combo debe contener los 10 proveedores.
3. Elige proveedor, monto, dirección y fecha; pulsa **Registrar**.
4. Comprueba la alerta exacta, el nuevo número y el estado **Pendiente**.
5. Pulsa **Editar**: comprueba que se carguen todos los campos y el proveedor.
6. Cambia proveedor, monto, dirección, fecha o estado y pulsa **Guardar**.
7. Comprueba la alerta exacta y que se modificó la misma orden.
8. Revisa el resultado en MySQL con `SELECT * FROM tbl_orden_compra ORDER BY nro_orden DESC;`.

## 9. Commits de Git

Se incluye el repositorio con commits reales `Primer cambio` (configuración, entidades y repositorios) y `Último cambio` (funcionalidades y guía), más `HISTORIAL_GIT.txt` y una copia portable `historial_git.bundle`. No se alteran fechas para simular otro horario.

El PDF exige que los commits evaluados ocurran dentro de tu intento. Esta entrega no puede confirmar ese horario. Si desarrollas el ejercicio en tu propio repositorio, coloca los nombres desde el inicio y realiza tus commits en el momento correspondiente:

```bash
git add .
git commit -m "Primer cambio"
# Después de implementar y probar las funcionalidades:
git add .
git commit -m "Último cambio"
```

Los commits incluidos registran el trabajo real de esta adaptación; no modifican fechas ni sustituyen el registro que deba ocurrir durante tu intento.

## Correspondencia con la rúbrica

| Pedido | Implementación |
| --- | --- |
| Entidades y relaciones JPA | Tres clases model con nombres HermozaJose, dos ManyToOne y tres AUTO_INCREMENT |
| Repositorios JpaRepository | RubroRepository, ProveedorRepository, OrdenCompraRepository |
| Servicios e implementaciones | Tres interfaces y tres clases ServiceImpl |
| Listado descendente y datos relacionados | Servicio, controller y tabla Thymeleaf |
| Monto con currency | #numbers.formatCurrency y locale es_PE |
| Registrar con combo | nuevo...html, /registrar, estado inicial P |
| Editar con datos y combo seleccionados | edicion...html, /edicion/{numero}, /guardar |
| Alertas exactas | Flash attributes y Alert.sweetToast |
| Dos commits | Repositorio incluido y guía de commits dentro del intento |
| Primer apellido y primer nombre | Hermoza y Jose colocados en proyecto, entidades y HTML |

Los campos y versiones corresponden a estos archivos; el material sirve para seguir el proceso y comprender qué hace cada componente.
