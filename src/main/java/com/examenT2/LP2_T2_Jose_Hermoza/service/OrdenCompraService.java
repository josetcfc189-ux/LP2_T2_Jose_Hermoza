package com.examenT2.LP2_T2_Jose_Hermoza.service;

import java.util.List;
import com.examenT2.LP2_T2_Jose_Hermoza.model.OrdenCompraHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.dto.ResultadoResponse;

public interface OrdenCompraService {
	List<OrdenCompraHermozaJose> getAll();
	OrdenCompraHermozaJose getOne(Integer numero);
	ResultadoResponse create(OrdenCompraHermozaJose ordenCompra);
	ResultadoResponse update(OrdenCompraHermozaJose ordenCompra);
}
