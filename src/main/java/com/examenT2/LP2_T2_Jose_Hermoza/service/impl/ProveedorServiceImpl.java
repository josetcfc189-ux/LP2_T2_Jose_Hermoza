package com.examenT2.LP2_T2_Jose_Hermoza.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examenT2.LP2_T2_Jose_Hermoza.model.ProveedorHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.repository.ProveedorRepository;
import com.examenT2.LP2_T2_Jose_Hermoza.service.ProveedorService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {
	private final ProveedorRepository proveedorRepository;

	@Override
	public List<ProveedorHermozaJose> getAll() {
		return proveedorRepository.findAllByOrderByIdProveedorDesc();
	}

	@Override
	public ProveedorHermozaJose getOne(Integer id) {
		return proveedorRepository.findById(id).orElse(null);
	}
}
