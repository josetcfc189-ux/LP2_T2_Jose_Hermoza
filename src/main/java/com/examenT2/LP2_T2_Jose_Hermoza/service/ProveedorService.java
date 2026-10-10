package com.examenT2.LP2_T2_Jose_Hermoza.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examenT2.LP2_T2_Jose_Hermoza.model.ProveedorHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorService {
	private final ProveedorRepository proveedorRepository;

	public List<ProveedorHermozaJose> getAll() {
		return proveedorRepository.findAllByOrderByIdProveedorDesc();
	}

	public ProveedorHermozaJose getOne(Integer id) {
		return proveedorRepository.findById(id).orElse(null);
	}
}
