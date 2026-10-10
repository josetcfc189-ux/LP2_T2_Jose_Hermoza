package com.examenT2.LP2_T2_Jose_Hermoza.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.examenT2.LP2_T2_Jose_Hermoza.dto.ResultadoResponse;
import com.examenT2.LP2_T2_Jose_Hermoza.model.OrdenCompraHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.repository.OrdenCompraRepository;
import com.examenT2.LP2_T2_Jose_Hermoza.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {
	private final OrdenCompraRepository ordenCompraRepository;
	private final ProveedorRepository proveedorRepository;

	public List<OrdenCompraHermozaJose> getAll() {
		return ordenCompraRepository.findAllByOrderByNroOrdenDesc();
	}

	public OrdenCompraHermozaJose getOne(Integer numero) {
		return ordenCompraRepository.findById(numero).orElse(null);
	}

	@Transactional
	public ResultadoResponse create(OrdenCompraHermozaJose ordenCompra) {
		// El formulario nuevo no permite cambiar el estado inicial.
		ordenCompra.setNroOrden(null);
		ordenCompra.setEstado("P");
		var error = validar(ordenCompra);
		if (error != null) return new ResultadoResponse(false, error);
		try {
			var registrado = ordenCompraRepository.saveAndFlush(ordenCompra);
			var mensaje = String.format("Orden de compra Nro. %d registrada.", registrado.getNroOrden());
			return new ResultadoResponse(true, mensaje);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ResultadoResponse(false, "Hubo un error en la transacción");
		}
	}

	@Transactional
	public ResultadoResponse update(OrdenCompraHermozaJose ordenCompra) {
		if (ordenCompra.getNroOrden() == null || !ordenCompraRepository.existsById(ordenCompra.getNroOrden())) {
			return new ResultadoResponse(false, "La orden de compra no existe.");
		}
		var error = validar(ordenCompra);
		if (error != null) return new ResultadoResponse(false, error);
		try {
			var registrado = ordenCompraRepository.saveAndFlush(ordenCompra);
			var mensaje = String.format("Orden de compra Nro. %d actualizada.", registrado.getNroOrden());
			return new ResultadoResponse(true, mensaje);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ResultadoResponse(false, "Hubo un error en la transacción");
		}
	}

	private String validar(OrdenCompraHermozaJose ordenCompra) {
		if (ordenCompra.getProveedor() == null || ordenCompra.getProveedor().getIdProveedor() == null
				|| !proveedorRepository.existsById(ordenCompra.getProveedor().getIdProveedor())) {
			return "Seleccione un proveedor válido.";
		}
		if (ordenCompra.getMonto() == null || ordenCompra.getMonto().signum() < 0
				|| ordenCompra.getMonto().compareTo(new BigDecimal("99999999.99")) > 0
				|| ordenCompra.getMonto().stripTrailingZeros().scale() > 2) {
			return "Ingrese un monto válido con máximo dos decimales.";
		}
		if (ordenCompra.getDireccionEntrega() == null || ordenCompra.getDireccionEntrega().isBlank()
				|| ordenCompra.getDireccionEntrega().length() > 255) {
			return "Ingrese una dirección de entrega de hasta 255 caracteres.";
		}
		if (ordenCompra.getFechaEntrega() == null) return "Ingrese una fecha de entrega válida.";
		if (!List.of("P", "C", "A").contains(ordenCompra.getEstado() == null ? "" : ordenCompra.getEstado())) {
			return "Seleccione un estado válido.";
		}
		return null;
	}
}
