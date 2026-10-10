package com.examenT2.LP2_T2_Jose_Hermoza.service;

import java.util.List;
import com.examenT2.LP2_T2_Jose_Hermoza.model.ProveedorHermozaJose;

public interface ProveedorService {
	List<ProveedorHermozaJose> getAll();
	ProveedorHermozaJose getOne(Integer id);
}
