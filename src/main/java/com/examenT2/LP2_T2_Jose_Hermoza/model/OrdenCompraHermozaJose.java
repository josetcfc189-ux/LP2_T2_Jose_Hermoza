package com.examenT2.LP2_T2_Jose_Hermoza.model;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_orden_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert

public class OrdenCompraHermozaJose {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nro_orden")
	private Integer nroOrden;

	@ManyToOne
	@JoinColumn(name = "id_proveedor", nullable = false)
	private ProveedorHermozaJose proveedor;

	@Column(name = "monto", nullable = false, precision = 10, scale = 2)
	private BigDecimal monto;

	@Column(name = "direccion_entrega", nullable = false, length = 255)
	private String direccionEntrega;

	@Column(name = "fecha_entrega", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaEntrega;

	@Column(name = "estado", length = 1, columnDefinition = "CHAR(1)")
	@ColumnDefault("'P'")
	private String estado = "P";

	public String getEstadoDescripcion() {
		if (estado == null) return "";
		return switch (estado) {
			case "P" -> "Pendiente";
			case "C" -> "Completado";
			case "A" -> "Anulado";
			default -> "";
		};
	}
	
}
	